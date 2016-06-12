package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.bitcup.whoseturn.storage.WhoseTurnDao;

public class HistoricalWhoseTurnService extends AbstractWhoseTurnService {

    private final WhoseTurnDao whoseTurnDao = new WhoseTurnDao();

    @Override
    public SpeechletResponse getStatedPersonsIntentResponse(Intent intent, Session session, SkillContext skillContext) {
        throw new IllegalStateException("Not implemented");
    }

}
