package com.bitcup.whoseturn.com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.bitcup.whoseturn.skill.WhoseTurnService;
import com.bitcup.whoseturn.utils.TestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author bitcup
 */
public class WhoseTurnServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoseTurnServiceTest.class);

    @Test
    public void testGetTwoStatedPersonsIntentResponse() throws Exception {
        Intent intent = TestUtils.getWhoseTurnIntentForTest("nadeem", "kaleem");
        WhoseTurnService service = new WhoseTurnService();
        SpeechletResponse response = service.getWhoseTurnIntentResponse(intent, TestUtils.getSessionForTest(), null);
        LOGGER.info("response: {}", response.getOutputSpeech().toString());
    }

    @Test
    public void testGetPersonsFromIntent() throws Exception {
        WhoseTurnService service = new WhoseTurnService();

        Intent intent = TestUtils.getWhoseTurnIntentForTest("nadeem", "kaleem", null, null);
        String[] persons = service.getPersons(intent);
        assertEquals(2, persons.length);
        assertEquals("nadeem", persons[0]);
        assertEquals("kaleem", persons[1]);

        Intent intent2 = TestUtils.getWhoseTurnIntentForTest("nadeem", "kaleem", "salma", null);
        String[] persons2 = service.getPersons(intent2);
        assertEquals(3, persons2.length);
        assertEquals("nadeem", persons2[0]);
        assertEquals("kaleem", persons2[1]);
        assertEquals("salma", persons2[2]);
    }
}
