package com.bitcup.whoseturn.com.bitcup.whoseturn.storage;

import com.bitcup.whoseturn.model.SelectionHistory;
import com.bitcup.whoseturn.storage.WhoseTurnDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author bitcup
 */
public class WhoseTurnDaoTest {

    private static final String DONALD = "donald";
    private static final String MAKE_AMERICA_GREAT_AGAIN = "make america great again";
    private static final String SAY_RACIST_THINGS = "say racist things";

    private static final String HILLARY = "hillary";
    private static final String MOVE_AMERICA_FORWARD = "move america forward";

    SelectionHistory selectionHistory;
    private WhoseTurnDao dao;
    private String userId;
    private String sessionId;

    @Before
    public void setup() throws Exception {
        dao = new WhoseTurnDao();
        userId = UUID.randomUUID().toString();
        sessionId = UUID.randomUUID().toString();
    }

    @After
    public void teardown() {
        if (selectionHistory != null) {
            dao.deleteSelectionHistory(selectionHistory);
        }
    }

    @Test
    public void testIntegration() throws Exception {
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertNull("selectionHistory is null", selectionHistory);

        selectionHistory = new SelectionHistory();
        selectionHistory.setSession(userId, sessionId);

        dao.saveSelectionHistory(selectionHistory);
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertNotNull("selectionHistory is not null", selectionHistory);
        assertTrue(selectionHistory.getHistory().isEmpty());

        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        dao.saveSelectionHistory(selectionHistory);
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertEquals(1, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());

        selectionHistory.increment(DONALD, MAKE_AMERICA_GREAT_AGAIN);
        dao.saveSelectionHistory(selectionHistory);
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());

        selectionHistory.increment(DONALD, SAY_RACIST_THINGS);
        dao.saveSelectionHistory(selectionHistory);
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        assertEquals(1, selectionHistory.getCount(SAY_RACIST_THINGS).get(DONALD).intValue());

        selectionHistory.increment(HILLARY, MOVE_AMERICA_FORWARD);
        dao.saveSelectionHistory(selectionHistory);
        selectionHistory = dao.getSelectionHistory(userId, sessionId);
        assertEquals(2, selectionHistory.getCount(MAKE_AMERICA_GREAT_AGAIN).get(DONALD).intValue());
        assertEquals(1, selectionHistory.getCount(SAY_RACIST_THINGS).get(DONALD).intValue());
        assertEquals(1, selectionHistory.getCount(MOVE_AMERICA_FORWARD).get(HILLARY).intValue());
    }
}
