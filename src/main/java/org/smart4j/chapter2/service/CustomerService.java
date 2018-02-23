package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 * Created by gcb on 2018/2/23.
 */
public class CustomerService {

    private final  static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            LOGGER.error("can not load jdbc driver ",e);
        }
    }


    /**
     * 获取客户列表
     * @return
     */
    public List<Customer>  getCustomerList(){
        Connection conn = null;
        try{
            List<Customer> customerList = new ArrayList<>();
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            String sql = "select * from customer";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs =stmt.executeQuery();
            while (rs.next()){
                Customer cust = new Customer();
                cust.setId(rs.getLong("id"));
                cust.setName(rs.getString("name"));
                cust.setContact(rs.getString("contact"));
                cust.setEmail(rs.getString("email"));
                cust.setRemark(rs.getString("remark"));
                cust.setTelephone(rs.getString("telephone"));
                customerList.add(cust);
            }
            return customerList;
        }catch (SQLException e){
            LOGGER.error("execute sql failure " ,e);
        }finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("close connnection failure " ,e);
                }
            }
        }
        return null;
    }

    public Customer getCustomer(long id){
        //TODO
        return null;
    }

    public boolean createCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    public boolean updateCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }

}
