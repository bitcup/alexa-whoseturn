package com.bitcup.whoseturn.com.bitcup.whoseturn.storage;

import com.amazon.speech.speechlet.Session;
import com.bitcup.whoseturn.model.SelectionHistory;
import com.bitcup.whoseturn.storage.DataMarshaller;
import com.bitcup.whoseturn.utils.TestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author omar
 */
public class DataMarshallerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataMarshallerTest.class);

    private static final String DONALD = "donald";
    private static final String MAKE_AMERICA_GREAT_AGAIN = "make america great again";
    private static final String SAY_RACIST_THINGS = "say racist things";

    private static final String HILLARY = "hillary";
    private static final String MOVE_AMERICA_FORWARD = "move america forward";

    @Test
    public void testSerDeser() throws Exception {

        SelectionHistory selectionHistory = new SelectionHistory();
        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        selectionHistory.increment(DONALD, SAY_RACIST_THINGS);
        selectionHistory.increment(HILLARY, MOVE_AMERICA_FORWARD);
        selectionHistory.setSession(TestUtils.getSessionForTest());
        LOGGER.info("selectionHistory = {}", selectionHistory);

        DataMarshaller marshaller = new DataMarshaller();
        String selectionHistoryString = marshaller.marshall(selectionHistory);
        LOGGER.info("selectionHistoryString = {}", selectionHistoryString);
        assertNotNull("selectionHistoryString not null", selectionHistoryString);

        SelectionHistory fromString = marshaller.unmarshall(SelectionHistory.class, selectionHistoryString);
        LOGGER.info("fromString = {}", fromString);
        assertNotNull("fromString not null", fromString);
        assertNull("session is null", fromString.getSession());
        assertEquals(selectionHistory, fromString);

        assertEquals(3, fromString.getHistory().size());

        assertTrue(fromString.getCount(MAKE_AMERICA_GREAT_AGAIN).keySet().contains(DONALD));
        assertEquals(2, fromString.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());

        assertTrue(fromString.getCount(SAY_RACIST_THINGS).keySet().contains(DONALD));
        assertEquals(1, fromString.getCount(SAY_RACIST_THINGS).get(DONALD).intValue());

        assertTrue(fromString.getCount(MOVE_AMERICA_FORWARD).keySet().contains(HILLARY));
        assertEquals(1, fromString.getCount(MOVE_AMERICA_FORWARD).get(HILLARY).intValue());
    }
}
