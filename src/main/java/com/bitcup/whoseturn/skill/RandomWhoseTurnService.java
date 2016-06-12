package com.bitcup.whoseturn.skill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.bitcup.whoseturn.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author omar
 */
public class RandomWhoseTurnService extends AbstractWhoseTurnService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomWhoseTurnService.class);

    public SpeechletResponse getStatedPersonsIntentResponse(Intent intent, Session session, SkillContext skillContext) {
        StatedPersonIntent statedPersonIntent = StatedPersonIntent.getByName(intent.getName());
        LOGGER.info("statedPersonIntent={}", statedPersonIntent);

        String activity = intent.getSlot(SLOT_ACTIVITY).getValue();
        LOGGER.info("intent activity={}", activity);
        if (activity == null && session.getAttribute("activity") == null) {
            LOGGER.info("intent activity is null and not in the session");
            String speechText = "I did not get the name of the activity. Could you repeat?";
            return getAskSpeechletResponse(speechText, speechText);
        } else if (activity == null && session.getAttribute("activity") != null) {
            activity = session.getAttribute("activity").toString();
            LOGGER.info("retrieved activity '{}' from session", activity);
        }
        session.setAttribute("activity", activity);

        LOGGER.info("FirstPersonName slot value = {}", intent.getSlot("FirstPersonName").getValue());
        LOGGER.info("SecondPersonName slot value = {}", intent.getSlot("SecondPersonName").getValue());

        String[] persons = statedPersonIntent.getPersons(intent);

        if (statedPersonIntent.hasNoPersons(intent)) {
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
}
