/**
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
 * <p/>
 * http://aws.amazon.com/apache2.0/
 * <p/>
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.bitcup.whoseturn.skill;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.bitcup.whoseturn.utils.Props;

import java.util.HashSet;
import java.util.Set;

public final class WhoseTurnSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> SUPPORTED_APPLICATION_IDS;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        SUPPORTED_APPLICATION_IDS = new HashSet<>();
        Props props = new Props("credz.properties");
        String supportedAppId = props.getString("supportedAppId");
        SUPPORTED_APPLICATION_IDS.add(supportedAppId);
    }

    public WhoseTurnSpeechletRequestStreamHandler() {
        super(new WhoseTurnSpeechlet(), SUPPORTED_APPLICATION_IDS);
    }
}
