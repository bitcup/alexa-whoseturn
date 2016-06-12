/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.bitcup.whoseturn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Util containing various text related utils.
 */
public final class TextUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextUtil.class);

    private static final List<String> NAME_BLACKLIST = Arrays.asList("player", "players");

    //public static final String HELP_TEXT = "Ask me whose turn it is to do something.  For example, whose turn is it to take the garbage, Jack or Diane?";
    public static final String HELP_TEXT = "I'm listening...";

    public static String getPersonName(String recognizedPersonName) {
        LOGGER.info("recognizedPersonName = {}", recognizedPersonName);
        if (recognizedPersonName == null || recognizedPersonName.isEmpty()) {
            LOGGER.info("recognizedPersonName empty or null - bailing", recognizedPersonName);
            return null;
        }

        String cleanedName;
        if (recognizedPersonName.contains(" ")) {
            // the name should only contain a first name, so ignore the second part if any
            cleanedName = recognizedPersonName.substring(recognizedPersonName.indexOf(" "));
        } else {
            cleanedName = recognizedPersonName;
        }
        LOGGER.info("cleanedName = {}", cleanedName);

        // if the name is on our blacklist, it must be mis-recognition
        if (NAME_BLACKLIST.contains(cleanedName)) {
            LOGGER.info("cleanedName in blacklist - bailing", cleanedName);
            return null;
        }

        return cleanedName;
    }
}
