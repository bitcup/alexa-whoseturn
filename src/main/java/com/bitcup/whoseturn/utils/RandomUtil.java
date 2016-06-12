package com.bitcup.whoseturn.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author omar
 */
public class RandomUtil {

    private static final SecureRandom SECURE_RANDOM;

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int getRandomlySelectedPersonIndex(int numPersons) {
        return getNextPositiveInt() % numPersons;
    }

    public static String getRandomlySelectedPerson(String... persons) {
        return persons[getNextPositiveInt() % persons.length];
    }

    public static int getRandomBetween(int lower, int upper) {
        return (int) (Math.random() * (upper - lower)) + lower;
    }

    private static int getNextPositiveInt() {
        int r = -1;
        while (r < 0) {
            r = SECURE_RANDOM.nextInt();
        }
        return r;
    }


}
