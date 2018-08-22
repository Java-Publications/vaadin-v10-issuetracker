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
package org.rapidpm.vaadin.v10.issuetracker.shiro;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringBasedServletInputStream extends ServletInputStream {

  private final InputStream sourceStream;
  private       boolean     finished = false;

  public StringBasedServletInputStream(String body) {
    sourceStream = new ByteArrayInputStream(body.getBytes());
  }

  @Override
  public int read() throws IOException {
    int data = sourceStream.read();
    if (data == -1) {
      finished = true;
    }
    return data;
  }

  @Override
  public int available() throws IOException {
    return sourceStream.available();
  }

  @Override
  public void close() throws IOException {
    super.close();
    sourceStream.close();
  }

  @Override
  public boolean isFinished() {
    return this.finished;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
    throw new UnsupportedOperationException();
  }

}
