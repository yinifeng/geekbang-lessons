package org.geektimes.projects.user.management;

import org.geektimes.projects.user.domain.User;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * 读
 * http://localhost:8080/jolokia/read/org.geektimes.projects.user.management:type=User/Name
 * 写
 * http://localhost:8080/jolokia/write/org.geektimes.projects.user.management:type=User/Name/hobart
 */
public class MBeanRegister {
    
    public static void register(){
        try {
            // 获取平台 MBean Server
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            // 为 UserMXBean 定义 ObjectName
            ObjectName objectName = new ObjectName("org.geektimes.projects.user.management:type=User");
            // 创建 UserMBean 实例
            User user = new User();
            user.setName("tom");
            mBeanServer.registerMBean(new UserManager(user), objectName);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
