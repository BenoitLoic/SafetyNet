package com.benoit.safetyAlert.utility;

/** Simple class to iterate a count child and adult with counter. */
public class Counter {

  private int child;
  private int adult;

  public Counter() {
    this.child=0;
    this.adult=0;
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
    int tempChild = child;
    int tempAdult = adult;
    String printCount = tempChild + " " + tempAdult;
    this.reset();
    return printCount;
  }
}
