package com.benoit.safetyAlert.utility;

/** Simple class to iterate a count child and adult with static counter. */
public class Counter {

  private  int child;
  private int adult;

  public Counter() {
    this.reset();
  }

  /** Increment child. */
  public void incrementChild() {
    child++;
  }

  /** Increment adult. */
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

  /** Reset. */
  public void reset() {
    child = 0;
    adult = 0;
  }

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
