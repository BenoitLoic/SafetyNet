package com.benoit.safetyAlert.utility;

/**
 * Simple class to iterate a count child and adult with static counter.
 */
public class Counter {

    private static int child;
    private static int adult;

    /**
     * Increment child.
     */
    public void incrementChild() {
        child++;
    }

    /**
     * Increment adult.
     */
    public void incrementAdult() {
        adult++;
    }

    /**
     * Gets child.
     *
     * @return the child
     */
    public int getChild() {
        return child;
    }

    /**
     * Gets adult.
     *
     * @return the adult
     */
    public int getAdult() {
        return adult;
    }

    /**
     * Reset.
     */
    public void reset() {
        child = 0;
        adult = 0;
    }
}
