package dbutils;

import com.entity.Customer;
import com.util.DBCPUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * apache-dbutils 是一个开源的工具类库，封装了JDBC的常用操作(CRUD)
 */
public class QueryRunnerTest {

    @Test
    public void testInsert() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "update customers set name=? where id=?";
        runner.update(connection,sql,"艾希",22);
    }

    /**查询单个对象*/
    @Test
    public void testQueryOne() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "SELECT NAME ,email ,birth birthday FROM customers WHERE id=?";
        ResultSetHandler rsh = new BeanHandler(Customer.class);
        Customer customer = (Customer) runner.query(connection, sql,rsh,1);
        System.out.println(customer);
    }

    /**查询多条数据*/
    @Test
    public void testQueryForList() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "SELECT id,NAME ,email ,birth birthday FROM customers WHERE id < ?";
        ResultSetHandler handler = new BeanListHandler(Customer.class);
        List<Customer> list = (List<Customer>) runner.query(connection,sql,handler,18);
        list.forEach(System.out::println);
    }

    /**查询多条数据以Map的形式呈现*/
    @Test
    public void testQueryForMap() throws Exception{
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "SELECT id,NAME ,email ,birth birthday FROM customers WHERE id < ?";
        ResultSetHandler handler = new MapListHandler();
        List<Map<String,Object>> list = (List<Map<String,Object>>) runner.query(connection,sql,handler,18);
        list.forEach(e -> System.out.println(e));
    }

    @Test
    public void testGetCount() throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "SELECT count(*) FROM customers";
        ResultSetHandler handler = new ScalarHandler();
        long count = (long) runner.query(connection,sql,handler);
        System.out.println(count);
    }

    /**
     * 自定义ResultSetHandler的实现
     * @throws Exception
     */
    @Test
    public void testMyResultSetHandler() throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection connection = DBCPUtils.getConnByDBCP();
        String sql = "SELECT id,NAME ,email ,birth birthday FROM customers WHERE id = ?";
        ResultSetHandler handler = (ResultSet resultSet) -> {
            Customer customer = new Customer();
            if(resultSet.next()){
                customer.setId(resultSet.getInt(1));
                customer.setName(resultSet.getString(2));
                customer.setEmail(resultSet.getString(3));
                customer.setBirthday(resultSet.getDate(4));
            }
            DbUtils.close(resultSet);
            return customer;
        };
        Customer customer = (Customer) runner.query(connection,sql,handler,21);
        System.out.println(customer);
        DbUtils.close(connection);
    }
}
