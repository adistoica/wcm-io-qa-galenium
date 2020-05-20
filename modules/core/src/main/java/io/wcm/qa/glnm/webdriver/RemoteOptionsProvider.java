/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2017 wcm.io
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
package io.wcm.qa.glnm.webdriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeOptions;


class RemoteOptionsProvider extends OptionsProvider<ChromeOptions> {

    String seleniumSessionCapabilities;
    String browserName;
    public RemoteOptionsProvider(String seleniumSessionCapabilities, String browserType) {
        super();
        this.seleniumSessionCapabilities = seleniumSessionCapabilities;
        this.browserName = browserType.substring(browserType.indexOf("_") + 1);
    }



    @Override
    protected ChromeOptions getBrowserSpecificOptions() {
        getLogger().debug("creating capabilities for Remote");

        ChromeOptions options = newOptions();

        JSONParser parser = new JSONParser();
        JSONObject config = null;

        try {
            config = (JSONObject) parser.parse(new FileReader(seleniumSessionCapabilities));
            System.out.println("Config Object: " + config.toJSONString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject envs = (JSONObject) config.get("environments");
        System.out.println("All environments: " + envs.toJSONString());

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(browserName.toLowerCase());
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            options.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (options.getCapability(pair.getKey().toString()) == null) {
                options.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
            options.setCapability("browserstack.user", username);
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
            options.setCapability("browserstack.access_key", accessKey);
        }

        return options;
    }

    @Override
    protected void log(ChromeOptions options) {
        if (getLogger().isTraceEnabled()) {
            getLogger().trace("generated capabilities: " + options);
            Object chromeOptionsCapability = options.getCapability(ChromeOptions.CAPABILITY);
            if (chromeOptionsCapability != null) {
                if (chromeOptionsCapability instanceof ChromeOptions) {
                    ChromeOptions chromeOptions = (ChromeOptions)chromeOptionsCapability;
                    Map<String, Object> json = chromeOptions.toJson();
                    Set<Entry<String, Object>> entrySet = json.entrySet();
                    StringBuilder sb = new StringBuilder();
                    sb.append("chromeOptions:\n");
                    for (Entry<String, Object> entry : entrySet) {
                        sb.append("'");
                        sb.append(entry.getKey());
                        sb.append("': '");
                        sb.append(entry.getValue());
                        sb.append("'");
                    }
                    getLogger().trace(sb.toString());
                }
            }
        }
    }

    @Override
    protected ChromeOptions newOptions() {
        return new ChromeOptions();
    }
}
