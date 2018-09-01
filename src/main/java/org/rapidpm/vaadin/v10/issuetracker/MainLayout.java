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
package org.rapidpm.vaadin.v10.issuetracker;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.rapidpm.vaadin.v10.issuetracker.components.header.HeaderComponent;

public class MainLayout extends Composite<VerticalLayout> {

  private HorizontalLayout header = new HorizontalLayout();
  private HorizontalLayout middle = new HorizontalLayout();
  private HorizontalLayout bottom = new HorizontalLayout();

  public MainLayout() {
    VerticalLayout wrappedLayout = getContent();

    header.setWidth("100%");
    HeaderComponent headerComponent = new HeaderComponent();
    header.add(headerComponent);
    header.expand(headerComponent);

    middle.setSizeFull();

    bottom.setWidth("100%");

    wrappedLayout.add(header, middle, bottom);

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