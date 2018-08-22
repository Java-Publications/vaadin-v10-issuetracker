/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.vaadin.v10.issuetracker;

public enum RolesToView {

  //TODO !!! MUST BE IN SYNC WITH shiro.ini ROLE NAMES !!!
  ADMIN("admin", "admin"),
  USER("user", "issue"),
  REPORTS("reports", "reports"),
  OBSERVER("observer", "search"),

  ROLE_B("roleB", "role-b"),
  ROLE_C("roleC", "role-c");


  private final String roleName;
  private final String viewName;

  RolesToView(String roleName, String viewName) {
    this.roleName = roleName;
    this.viewName = viewName;
  }

  public String roleName() {
    return roleName;
  }

  public String viewName() {
    return viewName;
  }

  @Override
  public String toString() {
    return viewName;
  }
}
