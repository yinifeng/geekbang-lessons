package org.geektimes.projects.user.web.listener;

import org.geektimes.projects.user.util.DataSourceUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {

    /*
     * 使用Resource注解成员变量，通过名字查找server.xml中配置的数据源并注入进来
     * lookup：指定目录处的名称，此属性是固定的
     * name：指定数据源的名称，即数据源处配置的name属性
     */
    //@Resource(lookup="java:/comp/env", name="jdbc/UserPlatformDB")
    //private DataSource dataSource;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //Context initCtx = new InitialContext();
            //Context envCtx = (Context) initCtx.lookup("java:comp/env");

            // Look up our data source
            //DataSource ds = (DataSource) initCtx.lookup("java:/comp/env/jdbc/UserPlatformDB");

            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)
                    envCtx.lookup("jdbc/UserPlatformDB");
            sce.getServletContext().log(ds+"----DBConnectionInitializerListener=====================>contextInitialized");
            DataSourceUtils.setDadaSource(ds);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
