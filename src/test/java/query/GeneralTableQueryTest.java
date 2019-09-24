package query;

import com.entity.Customer;
import com.entity.Order;
import com.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对不同表的通用查询操作
 */
public class GeneralTableQueryTest {

    /**对不同表的查询操作*/
    @Test
    public void testGeneralQuery() throws Exception{
        /**对customers表的查询操作*/
        Class clazz = Class.forName("com.entity.Customer");
        String sql = "select id,name,email,birth birthday from customers where id=?";
        Customer customer = (Customer) this.getInstance(clazz,sql,10);
        System.out.println(customer);
        /**对order表的查询操作*/
        Order order = this.getInstance(Order.class,"select order_name orderName,order_date orderDate from `order` where order_id=?",1);
        System.out.println(order);
    }

    /**查询表中的结果集*/
    @Test
    public void testList() throws Exception{
        String sql = "select id,name,email,birth birthday from customers";
        List<Customer> list = this.getForList(Customer.class,sql);
        list.forEach(System.out::println);
    }

    /**
     * 针对任一张表进行查询的操作
     */
    public <T> T getInstance(Class<T> clazz,String sql,Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(set.next()){
            /**通过反射，获取类的实体化对象*/
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = set.getObject(i + 1);
                /**getColumnLabel 获取列的别名*/
                String columnName = metaData.getColumnLabel(i + 1);
                Field field = t.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            return t;
        }
        JDBCUtils.closeConn();
        ps.close();
        set.close();
        return null;
    }

    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            /**为占位符设置参数*/
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<T> list = new ArrayList<>();
        while(set.next()){
            /**通过反射，获取类的实体化对象*/
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = set.getObject(i + 1);
                /**getColumnLabel 获取列的别名*/
                String columnName = metaData.getColumnLabel(i + 1);
                Field field = t.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            list.add(t);
        }
        JDBCUtils.closeConn();
        ps.close();
        set.close();
        return list;
    }
}
