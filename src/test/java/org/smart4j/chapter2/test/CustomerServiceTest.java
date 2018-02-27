package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gcb on 2018/2/23.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }


    @Before
    public void  init(){
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void  customerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void getCustomer(){
        Customer c = customerService.getCustomer(1);
        System.out.println("客戶姓名："+c.getName());
    }

    @Test
    public void insertCustomer(){
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name","蒲");
        fieldMap.put("contact","SONE");
        fieldMap.put("telephone","15612345678");
        fieldMap.put("email","156@126.com");
        fieldMap.put("remark","TEST類添加");
        boolean flag = customerService.createCustomer(fieldMap);
        Assert.assertEquals(flag,true);

    }

    @Test
    public void updateCustomer(){
        Customer c = customerService.getCustomer(2);
        c.setName("TEST修改了");
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name",c.getName());
        fieldMap.put("contact",c.getContact());
        fieldMap.put("telephone",c.getTelephone());
        fieldMap.put("email",c.getEmail());
        fieldMap.put("remark","改改改");
        boolean flag = customerService.updateCustomer(c.getId(),fieldMap);
        Assert.assertEquals(flag,true);
    }

    @Test
    public void deleteCustomer(){
        boolean flag = customerService.deleteCustomer(1);
        Assert.assertEquals(flag,true);
    }
}
