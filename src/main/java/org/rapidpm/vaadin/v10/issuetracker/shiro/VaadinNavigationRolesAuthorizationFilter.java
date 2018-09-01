/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.vaadin.v10.issuetracker.shiro;

import com.vaadin.flow.shared.ApplicationConstants;
import com.vaadin.flow.shared.JsonConstants;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import elemental.json.impl.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathConfigProcessor;
import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.apache.shiro.util.StringUtils.split;

public class VaadinNavigationRolesAuthorizationFilter extends AdviceFilter implements PathConfigProcessor {


  protected Map<String, List<String>> rolesByLocation = new LinkedHashMap<>();

  private String loginUrl;

  private String redirectJavaScript;

  @Override
  public Filter processPathConfig(String path, String config) {
    final List<String> roles = (config == null)
                               ? emptyList()
                               : asList(split(config));

    this.rolesByLocation.put(path, roles);
    return this;
  }

  public String getLoginUrl() {
    return loginUrl;
  }

  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  public String getRedirectJavaScript() {
    return redirectJavaScript;
  }

  public void setRedirectJavaScript(String redirectJavaScript) {
    this.redirectJavaScript = redirectJavaScript;
  }

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws IOException {
    HttpServletRequest  httpServletRequest  = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    String              uri                 = httpServletRequest.getRequestURI();

    return "/".equals(uri)
           ? handleFromBody(uri, httpServletRequest, httpServletResponse)
           : handleFromUri(uri, httpServletRequest, httpServletResponse);
  }

  protected boolean handleFromBody(String uri, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String  locationFromBody = getLocationFromBody(request);
    String  location         = locationFromBody != null ? locationFromBody : uri;
    boolean authorized       = authorized(location);

    if (!authorized) {
      String script = getRedirectJavaScript();
      if (script == null) {
        script = "location='" + loginUrl + "'";
      }
      response.getWriter().write("for(;;);[{\"execute\":[[\"" + script + "\"],[\"\",\"document.title = $0\"]]}]");
    }

    return authorized;
  }

  protected boolean handleFromUri(String location, HttpServletRequest request, HttpServletResponse response) {
    boolean authorized = authorized(location);

    if (!authorized) {
      response.setStatus(HttpServletResponse.SC_FOUND);
      response.setHeader("Location", loginUrl);
    }

    return authorized;
  }

  protected String getLocationFromBody(ServletRequest request) throws IOException {
    String body = request
        .getReader()
        .lines()
        .collect(joining());

    if (!body.isEmpty()) {
      JsonObject json = JsonUtil.parse(body);
      JsonArray  rpc  = json.getArray(ApplicationConstants.RPC_INVOCATIONS);
      if (rpc != null) {
        JsonObject value = rpc.get(0);
        JsonValue  type  = value.get(JsonConstants.RPC_TYPE);
        if (JsonConstants.RPC_TYPE_NAVIGATION.equals(type.asString())) {
          return "/" + value.getString(JsonConstants.RPC_NAVIGATION_LOCATION);
        }
      }
    }
    return null;
  }

  protected boolean authorized(String location) {
    List<String> roles = rolesByLocation.getOrDefault(location, emptyList());
    if (roles.isEmpty()) {
      return true;
    } else {
      Subject subject = SecurityUtils.getSubject();
      return roles
          .stream()
          .anyMatch(subject::hasRole);
    }
  }


}
