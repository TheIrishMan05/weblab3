package org.example.labwork3.beans;

public interface HitMBean {
    int getHits();
    int getSuccessfulHits();
    void checkForHitsOutOfBound(boolean isValid);
}
