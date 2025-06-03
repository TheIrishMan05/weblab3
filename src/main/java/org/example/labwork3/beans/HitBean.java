package org.example.labwork3.beans;

import java.util.concurrent.atomic.AtomicInteger;

import javax.management.*;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HitBean implements HitMBean {
    private final AtomicInteger hits = new AtomicInteger();
    private final AtomicInteger successfulHits = new AtomicInteger();

    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();

    @Override
    public int getHits() {
        return hits.get();
    }

    @Override
    public int getSuccessfulHits() {
        return successfulHits.get();
    }

    @Override
    public void checkForHitsOutOfBound(boolean isValid) {
        if(!isValid) {
            broadcaster.sendNotification(new Notification(
            "out.of.bounds",
            this,
            System.currentTimeMillis(),
            "This hit is out of bounds of plot."
            ));
        }
    }

    public void updateHits(boolean isHit){
        hits.incrementAndGet();
        if(isHit) {
            successfulHits.incrementAndGet();
        }
        checkForHitsOutOfBound(isHit);
    }


    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { "out.of.bounds" };
        String name = Notification.class.getName();
        String description = "Notification sent when hit is out of bounds of plot.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

}
