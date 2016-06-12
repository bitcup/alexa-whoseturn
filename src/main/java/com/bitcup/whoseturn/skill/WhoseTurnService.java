package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;

/**
 * @author omar
 */
public interface WhoseTurnService {
    SpeechletResponse getStatedPersonsIntentResponse(Intent intent, Session session, SkillContext skillContext);

    SpeechletResponse getLaunchResponse(LaunchRequest request, Session session);

    SpeechletResponse getHelpIntentResponse(Intent intent, Session session, SkillContext skillContext);

    SpeechletResponse getExitIntentResponse(Intent intent, Session session, SkillContext skillContext);
}
