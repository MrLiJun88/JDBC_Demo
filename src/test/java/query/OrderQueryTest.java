package query;

import com.entity.Order;
import com.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 针对于Order表的查询操作
 */
public class OrderQueryTest {

    @Test
    public void testQuery() throws Exception{
        String sql = "select * from `order`";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        while(set.next()){
            Order order = new Order();
            int id = set.getInt(1);
            String name = set.getString(2);
            Date date = set.getDate(3);
            order.setOrderId(id);
            order.setOrderName(name);
            order.setOrderDate(date);
            System.out.println(order);
        }
        JDBCUtils.closeConn();
        ps.close();
        set.close();
    }

    @Test
    public void testQueryForOrder() throws Exception{
        String sql = "select order_name orderName,order_date orderDate from `order` where order_id=?";
        Order order = this.queryForT_Order(sql,1);
        System.out.println(order);
    }
    /**
     * 针对于Order表的通用查询操作
     * 针对于表的字段名与类的属性名不一致的情况下
     * 必须给表中查询时字段取与类中属性名相同的别名
     * 必须使用getColumnLabel()方法获取类的别名，进行反射赋值
     * 如果没有给字段取别名，那么getColumnLabel()方法获取是类的字段名
     * */
    public Order queryForT_Order(String sql,Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        Order order = new Order();
        while(set.next()){
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = set.getObject(i + 1);
                /**getColumnLabel 获取列的别名*/
                String columnName = metaData.getColumnLabel(i + 1);
                Field field = Order.class.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(order,columnValue);
            }
        }
        JDBCUtils.closeConn();
        ps.close();
        set.close();
        return order;
    }
}
