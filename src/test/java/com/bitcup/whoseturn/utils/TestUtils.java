package com.bitcup.whoseturn.utils;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.bitcup.whoseturn.skill.AbstractWhoseTurnService;
import com.bitcup.whoseturn.skill.StatedPersonIntent;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author omar
 */
public class TestUtils {

    public static Intent getTwoStatedPersonsIntentForTest() {
        Map<String, Slot> slots = Maps.newHashMap();
        slots.put(AbstractWhoseTurnService.SLOT_ACTIVITY, Slot.builder().withName(AbstractWhoseTurnService.SLOT_ACTIVITY).withValue("maw the lawn").build());
        slots.put(StatedPersonIntent.TWO.getSlotNames()[0], Slot.builder().withName(StatedPersonIntent.TWO.getSlotNames()[0]).withValue("nadeem").build());
        slots.put(StatedPersonIntent.TWO.getSlotNames()[1], Slot.builder().withName(StatedPersonIntent.TWO.getSlotNames()[1]).withValue("kaleem").build());
        return Intent.builder().withName("WhoseTurnWithTwoStatedPersonsIntent").withSlots(slots).build();
    }

    public static Session getSessionForTest() {
        return Session.builder().withSessionId("1234").build();
    }
}
