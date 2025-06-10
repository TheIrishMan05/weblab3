package org.example.labwork3.beans;

public interface HitBeanMBean {
    int getHits();
    int getSuccessfulHits();
    void checkForHitsOutOfBound(boolean isValid);
}
