package com.daoimpl;

import com.dao.BaseDao;
import com.dao.CustomerDao;
import com.entity.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {

    @Override
    public Customer getOneById(Connection connection, int id) throws Exception{
        String sql = "select id,name,email,birth birthday from customers where id=?";
        Customer customer = this.getInstance(connection,sql,id);
        return customer;
    }

    @Override
    public void insertOne(Connection connection, Customer customer) throws Exception{
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        this.update(connection,sql,customer.getName(),customer.getEmail(),customer.getBirthday());
    }

    @Override
    public void deleteById(Connection connection, int id) {

    }

    @Override
    public void updateById(Connection connection, int id) {

    }

    @Override
    public List<Customer> getList(Connection connection) {
        return null;
    }

    @Override
    public Long getCount(Connection connection) throws Exception {
        String sql = "select count(*) from customers";
        long value = this.getValue(connection,sql);
        return value;
    }
}
