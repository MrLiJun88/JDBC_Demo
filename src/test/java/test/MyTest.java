package test;

import com.daoimpl.CustomerDaoImpl;
import com.entity.Customer;
import com.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * 测试CustomerDaoImpl中的各个方法
 */
public class MyTest {

    private CustomerDaoImpl customerDao = new CustomerDaoImpl();

    @Test
    public void testGetOne() throws Exception{
        Connection connection = JDBCUtils.getConnection();
        Customer customer = customerDao.getOneById(connection,2);
        System.out.println(customer);
    }

    @Test
    public void testInsert() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        Customer customer = new Customer();
        customer.setName("杨戬");
        customer.setEmail("dog@qq.com");
        customer.setBirthday(null);
        customerDao.insertOne(connection,customer);
    }

    @Test
    public void testCount() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        long count = customerDao.getCount(connection);
        System.out.println(count);
    }
}
