package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 数据库助手操作类
 * Created by gcb on 2018/2/26.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private final static QueryRunner QUERY_RUNNER = new QueryRunner();
    private final static String DRIVER;
    private final static String URL;
    private final static String USERNAME;
    private final static String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }
    }

    /**
     * 获取实体列表
     */
    public static <T>List<T> getEntityList(Class<T> entityClass,Connection conn,String sql,Object... params){
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
           LOGGER.error("query entity list failure",e);
           throw new RuntimeException(e);
        }finally {
            closeConnection(conn);
        }

        return entityList;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (Exception e){
            LOGGER.error("get connection failure",e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
            }
        }
    }
}
