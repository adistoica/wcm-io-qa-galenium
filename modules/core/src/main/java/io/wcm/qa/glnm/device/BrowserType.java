/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 - 2016 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.qa.glnm.device;

/**
 * Browser types that can be used with Galenium.
 */
public enum BrowserType {
  /** Chrome */
  CHROME("chrome"),
  /** Firefox */
  FIREFOX("firefox"),
  /** Internet Explorer */
  IE("ie"),
  /** Remote Chrome */
  REMOTE_CHROME("remote_chrome"),
  /** Remote firefox */
  REMOTE_FIREFOX("remote_firefox"),
  /** Remote IE */
  REMOTE_IE("remote_ie"),
  /** Remote Edge */
  REMOTE_EDGE("remote_edge"),
  /** Remote Safari */
  REMOTE_SAFARI("remote_safari"),
  /** Remote Opera */
  REMOTE_OPERA("remote_opera"),
  /** No browser. */
  NO_BROWSER("NO_BROWSER");

  private final String mBrowser;

  BrowserType(String pBrowser) {
    this.mBrowser = pBrowser;
  }

  /**
   * @return string representation of browser type to feed to Selenium
   */
  public String getBrowser() {
    return mBrowser;
  }
}
