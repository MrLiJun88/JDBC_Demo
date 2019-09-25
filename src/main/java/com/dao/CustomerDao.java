package com.dao;

import com.entity.Customer;

import java.sql.Connection;
import java.util.List;

/**
 * 定义针对于customers的常用操作
 */
public interface CustomerDao {

     Customer getOneById(Connection connection,int id) throws Exception;

     void insertOne(Connection connection,Customer customer) throws Exception;

     void deleteById(Connection connection,int id);

     void updateById(Connection connection,int id);

     List<Customer> getList(Connection connection);

     Long getCount(Connection connection) throws Exception;


}
