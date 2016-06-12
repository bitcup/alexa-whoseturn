package com.bitcup.whoseturn.com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.bitcup.whoseturn.skill.AbstractWhoseTurnService;
import com.bitcup.whoseturn.skill.RandomWhoseTurnService;
import com.bitcup.whoseturn.skill.StatedPersonIntent;
import com.bitcup.whoseturn.utils.TestUtils;
import com.google.common.collect.Maps;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author omar
 */
public class RandomWhoseTurnServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomWhoseTurnServiceTest.class);

    @Test
    public void testGetTwoStatedPersonsIntentResponse() throws Exception {
        Intent intent = TestUtils.getTwoStatedPersonsIntentForTest();
        RandomWhoseTurnService service = new RandomWhoseTurnService();
        SpeechletResponse response = service.getStatedPersonsIntentResponse(intent, TestUtils.getSessionForTest(), null);
        LOGGER.info("response: {}", response.getOutputSpeech().toString());
    }

}
