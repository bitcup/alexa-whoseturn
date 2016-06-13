package com.bitcup.whoseturn.utils;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author bitcup
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

    public static String getIntentAsString(Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(intent.getName()).append(", ");
        sb.append("slots=[\n");
        for (Slot s : intent.getSlots().values()) {
            sb.append("(n:").append(s.getName()).append(",v:").append(s.getValue()).append(")\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
