package framework.util;

/**
 * Util class for generating/handling randomization
 */

// TODO : use java.util.Random

import java.lang.Math;

public final class JRandomizer {

    private JRandomizer() {}

    public static Double getRandomValueBetween(int from, int to) {
        return (from + Math.random() * (to - from + 1) );
    }

    public static Long getRandomIntegerValueBetween(int from, int to) {
        return Math.round(getRandomValueBetween(from, to));
    }

    public static boolean eventOccurred(Double probability) {
        if(probability > 1.0) {
            // throw not supported error
        }

        return Math.random() <= probability;
    }



}