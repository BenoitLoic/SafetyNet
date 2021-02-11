package com.benoit.safetyAlert.utility;

/**
 * Simple class to count child and adult and get them.
 */
public class Counter {

  private int child;
  private int adult;

  public Counter() {
    this.reset();
  }

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
   * Reset the counter.
   */
  public void reset() {
    child = 0;
    adult = 0;
  }

  /**
   * This method add one adult if age is superior or equal to 18, if not it will add one child.
   *
   * @param age the age to count
   */
  public void process(int age) {
    final int adultAge = 18;
    if (age >= adultAge) {
      incrementAdult();
    } else {
      incrementChild();
    }
  }

  public String getAll() {
    return "child: " + child + " - " + "adult: " + adult;
  }
}
