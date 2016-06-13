package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.bitcup.whoseturn.utils.RandomUtil;
import com.bitcup.whoseturn.utils.TextUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author bitcup
 */
public class WhoseTurnService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoseTurnService.class);

    public static final String SLOT_ACTIVITY = "Activity";
    public static final String[] SLOT_PERSONS = {"FirstPersonName", "SecondPersonName", "ThirdPersonName", "FourthPersonName"};

    private static final String[] RESPONSE_SUFFIXES = {
            "",
            "They haven't done it in a while.",
            "They’ve been slacking off lately.",
            "It's better than watching youtube.",
            "It's better than watching playing on the their phone.",
            "They’re on the phone all day otherwise.",
            "They’re on youtube all day otherwise.",
            "They don’t do much else around here.",
            "They’re really good at it.",
            "They’re the best at it.",
            "They’re masters at it."
    };

    public SpeechletResponse getWhoseTurnIntentResponse(Intent intent, Session session, SkillContext skillContext) {
        String activity = intent.getSlot(SLOT_ACTIVITY).getValue();
        if (activity == null && session.getAttribute("activity") == null) {
            LOGGER.info("intent activity is null and not in the session");
            String speechText = "I did not get the name of the activity. Could you repeat?";
            return getAskSpeechletResponse(speechText, speechText);
        } else if (activity == null && session.getAttribute("activity") != null) {
            activity = session.getAttribute("activity").toString();
            LOGGER.info("retrieved activity '{}' from session", activity);
        }
        session.setAttribute("activity", activity);

        String[] persons = getPersons(intent);
        if (hasNoPersons(intent)) {
            LOGGER.info("persons are null or empty");
            String speechText = "Who do you think should do that?";
            return getAskSpeechletResponse(speechText, speechText);
        }
        LOGGER.info("persons={}", persons);
        for (String person : persons) {
            if (person == null) {
                String speechText = "I did not get the name of all persons. Could you repeat them?";
                return getAskSpeechletResponse(speechText, speechText);
            }
        }

        String selected = RandomUtil.getRandomlySelectedPerson(persons);
        LOGGER.info("selected={}", selected);
        String speechText = decorateResponse("I think it is " + selected + "'s turn to " + activity + ".");
        return getTellSpeechletResponse(speechText);
    }

    public SpeechletResponse getLaunchResponse(LaunchRequest request, Session session) {
        return getAskSpeechletResponse(TextUtil.HELP_TEXT, TextUtil.HELP_TEXT);
    }

    public SpeechletResponse getHelpIntentResponse(Intent intent, Session session, SkillContext skillContext) {
        return skillContext.needsMoreHelp() ?
                getAskSpeechletResponse(TextUtil.HELP_TEXT + " So, how can I help?", TextUtil.HELP_TEXT) : getTellSpeechletResponse(TextUtil.HELP_TEXT);
    }

    public SpeechletResponse getExitIntentResponse(Intent intent, Session session, SkillContext skillContext) {
        return getTellSpeechletResponse("Bye now!");
    }

    private SpeechletResponse getAskSpeechletResponse(String speechText, String repromptText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Whose turn is it?");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse getTellSpeechletResponse(String speechText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Whose turn is it?");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    private String decorateResponse(String response) {
        return response + " " + RESPONSE_SUFFIXES[RandomUtil.getRandomBetween(0, RESPONSE_SUFFIXES.length - 1)];
    }

    /*
    private Card getLeaderboardScoreCard(Map<String, Long> scores) {
        StringBuilder leaderboard = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, Long> entry : scores.entrySet()) {
            index++;
            leaderboard
                    .append("No. ")
                    .append(index)
                    .append(" - ")
                    .append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append("\n");
        }

        SimpleCard card = new SimpleCard();
        card.setTitle("Leaderboard");
        card.setContent(leaderboard.toString());
        return card;
    }
    */

    public String[] getPersons(Intent intent) {
        List<String> persons = Lists.newArrayList();
        for (String SLOT_PERSON : SLOT_PERSONS) {
            Slot slot = intent.getSlot(SLOT_PERSON);
            if (slot != null) {
                final String personName = TextUtil.getPersonName(slot.getValue());
                if (personName != null) {
                    persons.add(personName);
                }
            }
        }
        LOGGER.info("for intent: {}, persons={}", intent.getName(), persons);
        return persons.toArray(new String[persons.size()]);
    }

    private boolean hasNoPersons(Intent intent) {
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

    private String getPersonOrder(int i) {
        if (i == 0) return "first";
        if (i == 1) return "second";
        if (i == 2) return "third";
        if (i == 3) return "fourth";
        throw new IllegalArgumentException("Unsupported number of persons: " + i);
    }

}
