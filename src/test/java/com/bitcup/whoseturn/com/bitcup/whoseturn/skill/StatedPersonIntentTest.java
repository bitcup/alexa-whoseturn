package com.bitcup.whoseturn.com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.bitcup.whoseturn.skill.StatedPersonIntent;
import com.bitcup.whoseturn.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author omar
 */
public class StatedPersonIntentTest {
    @Test
    public void testStatedPersonIntent() throws Exception {
        Intent intent = TestUtils.getTwoStatedPersonsIntentForTest();

        StatedPersonIntent statedPersonIntent = StatedPersonIntent.getByName(intent.getName());
        assertEquals(StatedPersonIntent.TWO, statedPersonIntent);

        String[] persons = statedPersonIntent.getPersons(intent);
        assertEquals(2, persons.length);
        assertEquals("nadeem", persons[0]);
        assertEquals("kaleem", persons[1]);
    }
}