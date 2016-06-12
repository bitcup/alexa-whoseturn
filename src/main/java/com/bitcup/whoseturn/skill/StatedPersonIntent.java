package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.bitcup.whoseturn.utils.TextUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author omar
 */
public enum StatedPersonIntent {
    TWO("WhoseTurnWithTwoStatedPersonsIntent", Lists.newArrayList("FirstPersonName", "SecondPersonName").stream().toArray(String[]::new)),
    THREE("WhoseTurnWithThreeStatedPersonsIntent", Lists.newArrayList("FirstPersonName", "SecondPersonName", "ThirdPersonName").stream().toArray(String[]::new)),
    FOUR("WhoseTurnWithFourStatedPersonsIntent", Lists.newArrayList("FirstPersonName", "SecondPersonName", "ThirdPersonName", "FourthPersonName").stream().toArray(String[]::new));

    private static final Logger LOGGER = LoggerFactory.getLogger(StatedPersonIntent.class);

    private String name;
    private String[] slotNames;

    StatedPersonIntent(String name, String[] slotNames) {
        this.name = name;
        this.slotNames = slotNames;
    }

    public String getIntentName() {
        return name;
    }

    public String[] getSlotNames() {
        return slotNames;
    }

    public String[] getPersons(Intent intent) {
        LOGGER.info("for intent {}, slotNames={}", name, slotNames);
        String[] persons = new String[slotNames.length];
        for (int i = 0; i < slotNames.length; i++) {
            Slot slot = intent.getSlot(slotNames[i]);
            if (slot == null) {
                throw new IllegalStateException("Could not find slot with name " + slotNames[i] + " in intent " + intent.getName());
            }
            LOGGER.info("for intent {}, slot with name {}, value=", name, slot.getName(), slot.getValue());
            persons[i] = TextUtil.getPersonName(slot.getValue());
            LOGGER.info("for intent {}, persons[{}]={}", name, i, persons[i]);
        }
        return persons;
    }

    public boolean hasNoPersons(Intent intent) {
        String[] persons = getPersons(intent);
        if (persons == null || persons.length == 0) {
            return true;
        }
        boolean allNull = true;
        for (String p : persons) {
            if (p != null) {
                allNull = false;
                break;
            }
        }
        return allNull;
    }

    public String getPersonOrder(int i) {
        if (i == 0) return "first";
        if (i == 1) return "second";
        if (i == 2) return "third";
        if (i == 3) return "fourth";
        throw new IllegalArgumentException("Unsupported number of persons: " + i);
    }

    public static StatedPersonIntent getByName(String name) {
        for (StatedPersonIntent intent : values()) {
            if (intent.name.equals(name)) {
                return intent;
            }
        }
        throw new IllegalArgumentException("Unknown intent: " + name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("slotNames", slotNames)
                .toString();
    }
}