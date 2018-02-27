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
            String sql = "select * from customer";
            return DatabaseHelper.queryEntityList(Customer.class,sql);
    }


    /**
     * 获取客户
     */
    public Customer getCustomer(long id){
        String sql = "SELECT * FROM customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class,sql,id);
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }

}
