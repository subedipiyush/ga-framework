package framework;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    /**
     * Returns a random integer between 0 and 1
     */
    public static int randInt() {
        return randInt(0, 1);
    }

    /**
     * Returns a random integer between the specified range
     * @param min
     * @param max
     */
    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Returns <code>true</code> if a randomized event occurred based on the given probability of occurrence
     * It's like flipping a biased coin with the bias given by probability
     * @param probability bias
     * @return
     */
    public static boolean eventOccurred(double probability) {
        return randInt() <= probability;
    } 

}