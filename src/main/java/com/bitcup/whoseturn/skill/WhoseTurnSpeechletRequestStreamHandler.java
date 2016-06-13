package com.bitcup.whoseturn.skill;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.bitcup.whoseturn.utils.Props;

import java.util.HashSet;
import java.util.Set;

/**
 * @author bitcup
 */
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
