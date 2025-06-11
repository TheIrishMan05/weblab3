package org.example.labwork3.utils;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

public class MBeanRegisterUtil {
    private static final Map<Object, ObjectName> beanMap = new HashMap<>();

    public static void registerBean(Object bean, String beanName) {
        try {
            String domain = "org.example.labwork3";
            ObjectName objectName = new ObjectName(domain + ":type=" + beanName);
            
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            
        
            mBeanServer.registerMBean(bean, objectName);
            beanMap.put(bean, objectName);
            
            System.out.println("Successfully registered MBean: " + objectName);
        } catch (Exception e) {
            System.err.println("Failed to register MBean: " + beanName);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void unregisterBean(Object bean) {
        try {
            ObjectName objectName = beanMap.get(bean);
            if (objectName != null) {
                ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName);
                beanMap.remove(bean);
                System.out.println("Successfully unregistered MBean: " + objectName);
            }
        } catch (Exception e) {
            System.err.println("Failed to unregister MBean: " + bean.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}