package com.bitcup.whoseturn.utils;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author bitcup
 */
public class RandomUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtilTest.class);

    String[] persons = {"p1", "p2"};
    Map<String, Integer> scoresPerPerson = Maps.newHashMap();
    Map<Integer, Integer> scoresPerIndex = Maps.newHashMap();

    @Before
    public void setup() throws Exception {
        scoresPerPerson.clear();
        for (String p : persons) {
            scoresPerPerson.put(p, 0);
        }
        scoresPerIndex.clear();
        for (int i = 0; i < persons.length; i++) {
            scoresPerIndex.put(i, 0);
        }
    }

    @Test
    public void testGetRandomlySelectedPerson() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String selected = RandomUtil.getRandomlySelectedPerson(persons);
            Integer currentScore = scoresPerPerson.get(selected);
            scoresPerPerson.put(selected, currentScore + 1);
        }
        LOGGER.info("scoresPerPerson = {}, took {}ms", scoresPerPerson, System.currentTimeMillis() - start);
        assertEquals(2, scoresPerPerson.size());
        for (String p : persons) {
            assertTrue(scoresPerPerson.get(p) > 0);
        }
    }

    @Test
    public void testGetRandomlySelectedPersonIndex() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            int selected = RandomUtil.getRandomlySelectedPersonIndex(persons.length);
            Integer currentScore = scoresPerIndex.get(selected);
            scoresPerIndex.put(selected, currentScore + 1);
        }
        LOGGER.info("scoresPerIndex = {}, took {}ms", scoresPerIndex, System.currentTimeMillis() - start);
        assertEquals(2, scoresPerIndex.size());
        for (int i = 0; i < persons.length; i++) {
            assertTrue(scoresPerIndex.get(i) > 0);
        }
    }
}
