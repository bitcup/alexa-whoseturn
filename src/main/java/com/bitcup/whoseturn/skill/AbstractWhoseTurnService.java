package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.bitcup.whoseturn.utils.RandomUtil;
import com.bitcup.whoseturn.utils.TextUtil;

/**
 * @author omar
 */
public abstract class AbstractWhoseTurnService implements WhoseTurnService {

    public static final String SLOT_ACTIVITY = "Activity";

    protected static final String[] RESPONSE_SUFFIXES = {
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

    protected SpeechletResponse getAskSpeechletResponse(String speechText, String repromptText) {
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

    protected SpeechletResponse getTellSpeechletResponse(String speechText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Whose turn is it?");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    protected String decorateResponse(String response) {
        return response + " " + RESPONSE_SUFFIXES[RandomUtil.getRandomBetween(0, RESPONSE_SUFFIXES.length - 1)];
    }

    /*
    protected Card getLeaderboardScoreCard(Map<String, Long> scores) {
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


}
