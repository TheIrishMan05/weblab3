package org.example.labwork3.beans;

import java.util.concurrent.atomic.AtomicLong;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClickIntervalBean implements ClickIntervalBeanMBean {
    private final AtomicLong lastClickTime = new AtomicLong(0);
    private final AtomicLong sumIntervals = new AtomicLong(0);
    private final AtomicLong countIntervals = new AtomicLong(0);

    /**
     * Call this method each time user clicks on the coordinate plane
     */
    public void recordClick() {
        long now = System.currentTimeMillis();
        long prev = lastClickTime.getAndSet(now);
        if (prev != 0) {
            long delta = now - prev;
            sumIntervals.addAndGet(delta);
            countIntervals.incrementAndGet();
        }
    }

    @Override
    public double getAverageIntervalMillis() {
        long count = countIntervals.get();
        return count == 0 ? 0.0 : ((double) sumIntervals.get() / count);
    }
}