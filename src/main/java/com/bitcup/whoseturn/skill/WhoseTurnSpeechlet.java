package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhoseTurnSpeechlet implements Speechlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoseTurnSpeechlet.class);

    private WhoseTurnService whoseTurnService = new RandomWhoseTurnService();
    private SkillContext skillContext = new SkillContext();

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        LOGGER.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        skillContext.setNeedsMoreHelp(false);
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        LOGGER.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        skillContext.setNeedsMoreHelp(true);
        return whoseTurnService.getLaunchResponse(request, session);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        LOGGER.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        Intent intent = request.getIntent();
        switch (intent.getName()) {
            case "WhoseTurnWithTwoStatedPersonsIntent":
            case "WhoseTurnWithThreeStatedPersonsIntent":
            case "WhoseTurnWithFourStatedPersonsIntent":
                return whoseTurnService.getStatedPersonsIntentResponse(intent, session, skillContext);
            case "AMAZON.HelpIntent":
                return whoseTurnService.getHelpIntentResponse(intent, session, skillContext);
            case "AMAZON.CancelIntent":
                return whoseTurnService.getExitIntentResponse(intent, session, skillContext);
            case "AMAZON.StopIntent":
                return whoseTurnService.getExitIntentResponse(intent, session, skillContext);
            default:
                throw new IllegalArgumentException("Unrecognized intent: " + intent.getName());
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        LOGGER.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }
}
