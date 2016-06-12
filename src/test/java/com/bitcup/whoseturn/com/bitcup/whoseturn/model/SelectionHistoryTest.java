package com.bitcup.whoseturn.com.bitcup.whoseturn.model;

import com.bitcup.whoseturn.model.SelectionHistory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author omar
 */
public class SelectionHistoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectionHistoryTest.class);

    private static final String DONALD = "donald";
    private static final String MAKE_AMERICA_GREAT_AGAIN = "make america great again";
    private static final String SAY_RACIST_THINGS = "say racist things";

    private static final String HILLARY = "hillary";
    private static final String MOVE_AMERICA_FORWARD = "move america forward";

    @Test
    public void testModel() throws Exception {
        SelectionHistory selectionHistory = new SelectionHistory();
        assertEquals(0, selectionHistory.getHistory().size());

        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        assertEquals(1, selectionHistory.getHistory().size());
        assertTrue(selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).keySet().contains(DONALD));
        assertEquals(1, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        LOGGER.info("selectionHistory: {}", selectionHistory);

        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        assertEquals(1, selectionHistory.getHistory().size());
        assertTrue(selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).keySet().contains(DONALD));
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        LOGGER.info("selectionHistory: {}", selectionHistory);

        selectionHistory.increment(DONALD, SAY_RACIST_THINGS);
        assertEquals(2, selectionHistory.getHistory().size());
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        assertEquals(1, selectionHistory.getCount(SAY_RACIST_THINGS).get(DONALD).intValue());
        LOGGER.info("selectionHistory: {}", selectionHistory);

        selectionHistory.increment(HILLARY, MOVE_AMERICA_FORWARD);
        assertEquals(3, selectionHistory.getHistory().size());
        assertTrue(selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).keySet().contains(DONALD));
        assertTrue(selectionHistory.getCount(MOVE_AMERICA_FORWARD).keySet().contains(HILLARY));
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        LOGGER.info("selectionHistory: {}", selectionHistory);
    }

}
