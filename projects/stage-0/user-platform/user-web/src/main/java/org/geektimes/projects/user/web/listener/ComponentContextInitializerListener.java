package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * {@link ComponentContext} 初始化器
 * ContextLoaderListener
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        ComponentContext context = new ComponentContext();
        context.init(servletContext);
        //初始化ddl脚本
        initDDL(context);
        Map<String, ? extends ServletRegistration> servletRegistrations = this.servletContext.getServletRegistrations();
        servletRegistrations.forEach((key,value)->{
            String className = value.getClassName();
            servletContext.log("++++++++++++++++="+className);
        });
    }

    private void initDDL(ComponentContext context) {
        DBConnectionManager dbConnectionManager = context.getComponent("bean/DBConnectionManager");
        Connection connection = dbConnectionManager.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            // 删除 users 表
            statement.execute(DBConnectionManager.DROP_USERS_TABLE_DDL_SQL); // false
            // 创建 users 表
            statement.execute(DBConnectionManager.CREATE_USERS_TABLE_DDL_SQL); // false
            statement.executeUpdate(DBConnectionManager.INSERT_USER_DML_SQL);
        } catch (SQLException e) {
            try {
                statement.execute(DBConnectionManager.CREATE_USERS_TABLE_DDL_SQL); // false
                statement.executeUpdate(DBConnectionManager.INSERT_USER_DML_SQL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ComponentContext context = ComponentContext.getInstance();
//        context.destroy();
    }

}
 