package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
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




    /**
     * 获取客户列表
     * @return
     */
    public List<Customer>  getCustomerList(){
        Connection conn = null;
        try{
            List<Customer> customerList = new ArrayList<>();
            conn = DatabaseHelper.getConection();
            String sql = "select * from customer";
            conn = DatabaseHelper.getConection();
            customerList = DatabaseHelper.getEntityList(Customer.class,conn,sql);
            return customerList;
        }finally {
           DatabaseHelper.closeConnection(conn);
        }
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
