package com.bitcup.whoseturn.utils;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.bitcup.whoseturn.skill.WhoseTurnService;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author bitcup
 */
public class TestUtils {

    public static Intent getWhoseTurnIntentForTest(String... persons) {
        Map<String, Slot> slots = Maps.newHashMap();
        slots.put(WhoseTurnService.SLOT_ACTIVITY, Slot.builder().withName(WhoseTurnService.SLOT_ACTIVITY).withValue("maw the lawn").build());
        for (int i = 0; i < persons.length; i++) {
            slots.put(WhoseTurnService.SLOT_PERSONS[i], Slot.builder().withName(WhoseTurnService.SLOT_PERSONS[i]).withValue(persons[i]).build());
        }
        return Intent.builder().withName("WhoseTurnIntent").withSlots(slots).build();
    }

    public static Session getSessionForTest() {
        return Session.builder().withSessionId("1234").build();
    }
}
