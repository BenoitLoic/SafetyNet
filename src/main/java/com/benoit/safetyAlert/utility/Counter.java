package com.benoit.safetyAlert.utility;

public class Counter {

    private static int child;
    private static int adult;


    public void incrementChild(){
        child++;
    }
    public void incrementAdult(){
        adult++;
    }
    public int getChild() {
        return child;
    }


    public int getAdult() {
        return adult;
    }

    public void reset() {
        child = 0;
        adult = 0;
    }

}
