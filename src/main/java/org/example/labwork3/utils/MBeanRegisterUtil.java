package org.example.labwork3.utils;

import java.lang.management.ManagementFactory;
import java.util.HashMap;

import jakarta.servlet.ServletContextListener;
import lombok.experimental.UtilityClass;
import javax.management.*;


@UtilityClass
public class MBeanRegisterUtil implements ServletContextListener{
    private final HashMap<Class<?>, ObjectName> beanMap = new HashMap<>();

    public void registerBean(Object bean, String name){
        try {
            String domain = bean.getClass().getPackageName();
            String type = bean.getClass().getSimpleName();
            ObjectName objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, name));
            ManagementFactory.getPlatformMBeanServer().registerMBean(bean, objectName);
            beanMap.put(bean.getClass(), objectName);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException |
                MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    public void unregisterBean(Object bean){
        if (!beanMap.containsKey(bean.getClass())) {
            throw new IllegalArgumentException("Specified bean is not registered.");
        }
        try {
            ObjectName objectName = beanMap.get(bean.getClass());
            if (objectName != null) {
                ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName);
            }
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
