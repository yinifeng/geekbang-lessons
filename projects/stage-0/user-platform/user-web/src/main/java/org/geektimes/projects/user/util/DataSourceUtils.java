package org.geektimes.projects.user.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {

    private final static ThreadLocal<Connection> LOCAL=new ThreadLocal();
    private static DataSource dataSource;

    public static void setDadaSource(DataSource dataSource) {
        /*不能使用this*/
        DataSourceUtils.dataSource=dataSource;
    }

    /*返回连接对象*/
    public static Connection getConnection() {
        try {
            /*获取连接对象*/
            Connection conn=LOCAL.get();
            if(null != conn) {
                return conn;
            }

            /*通过数据源得到连接，并放入线程中管理，再返回连接对象*/
            conn=dataSource.getConnection();
            LOCAL.set(conn);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*释放连接对象*/
    public static void release() {
        Connection conn=LOCAL.get();
        if(null != conn) {
            LOCAL.remove();
        }
    }
}
