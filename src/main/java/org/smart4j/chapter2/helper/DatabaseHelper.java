package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.CollectionUtil;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库助手操作类
 * Created by gcb on 2018/2/26.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private final static ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
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
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass , long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql,id)==1;
    }
    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass ,long id, Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update eneity : fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder(128).append(" ( ");

        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append("=?,");
        }

        sql += columns.replace(columns.lastIndexOf(","),columns.length(),")") + " where id = ? ";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);

        Object[] params = paramList.toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass , Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity : fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder(128).append(" ( ");
        StringBuilder values = new StringBuilder(128).append(" ( ");

        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append(" ?, ");
        }

        columns = columns.replace(columns.lastIndexOf(","),columns.length()," ) ");
        values = values.replace(values.lastIndexOf(","),values.length()," ) ");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql,params) == 1;
    }

    /**
     * 获取实体
     */
    public static <T>T queryEntity(Class<T> entityClass,String sql , Object... params){
        T entity;
        try {
            Connection conn =CONNECTION_THREAD_LOCAL.get();
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
           LOGGER.error("query entity failure",e);
           throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }
    /**
     * 获取表名
     */
    public static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * 执行更新语句(包括insert、update、delete语句)
     */
    public static int executeUpdate(String sql , Object... params){
        int rows = 0;
        try {
            Connection conn = getConection();
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure ",e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 执行查询语句
     */
    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> result = null;

        try {
            Connection conn = getConection();
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
           LOGGER.error("execute query failure",e);
           throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 获取实体列表
     */
    public static <T>List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList;
        try {
            Connection conn = CONNECTION_THREAD_LOCAL.get();
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
           LOGGER.error("query entity list failure",e);
           throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConection(){
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if (conn == null) {
            try {
                    conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            }catch (Exception e){
                LOGGER.error("get connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }
}
