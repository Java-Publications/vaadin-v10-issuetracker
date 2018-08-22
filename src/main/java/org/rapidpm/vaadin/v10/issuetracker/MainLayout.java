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

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.components.header.HeaderComponent;
import org.rapidpm.vaadin.v10.issuetracker.components.header.HeaderComponentFactory;

//@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends Composite<VerticalLayout> implements HasLogger {


  private final HeaderComponent headerComponent = new HeaderComponentFactory().createBasedOnRoles();

  private final HorizontalLayout header = new HorizontalLayout(
      headerComponent
  ) {{
    setWidth("100%");
    expand(headerComponent);
  }};

  private final HorizontalLayout middle = new HorizontalLayout(
  ) {{
    setSizeFull();
  }};

  private final HorizontalLayout bottom = new HorizontalLayout(
  ) {{
    setWidth("100%");
  }};

  public MainLayout() {
    logger().info("setting now the main layout ui ..");
    VerticalLayout wrappedLayout = getContent();


    wrappedLayout.add(header,
                      middle,
                      bottom
    );

    wrappedLayout.setAlignSelf(FlexComponent.Alignment.START, header);
    wrappedLayout.expand(header);

    wrappedLayout.setAlignSelf(FlexComponent.Alignment.STRETCH, middle);
    wrappedLayout.setFlexGrow(1, middle);

    wrappedLayout.setAlignSelf(FlexComponent.Alignment.BASELINE, bottom);
    wrappedLayout.expand(bottom);

    wrappedLayout.setSizeFull();

  }

  public HorizontalLayout header() {
    return header;
  }

  public HorizontalLayout middle() {
    return middle;
  }

  public HorizontalLayout bottom() {
    return bottom;
  }

}