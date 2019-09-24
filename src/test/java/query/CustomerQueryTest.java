package query;

import com.entity.Customer;
import com.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 针对于Customer表的查询操作
 */
public class CustomerQueryTest {

    @Test
    public void testQuery(){
        String sql = "select * from customers where id=?";
        try(PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql)){
            ps.setObject(1,12);
            ResultSet set = ps.executeQuery();
            Customer customer = new Customer();
            /**获取查询后的结果集*/
            while(set.next()){
                int id = set.getInt(1);
                String name = set.getString(2);
                String email = set.getString(3);
                Date birthday = set.getDate(4);
                String photo = set.getString(5);
                customer.setId(id);
                customer.setName(name);
                customer.setEmail(email);
                customer.setBirthday(birthday);
                customer.setPhoto(photo);
            }
            System.out.println(customer);
        }
        catch (Exception e ){
            e.printStackTrace();
        }
        finally {
            try{
                JDBCUtils.closeConn();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testQueryForCustomer(){
        String sql = "select id,name,email from customers where id=?";
        Customer customer = this.queryForT_Customer(sql,13);
        System.out.println(customer);
    }
    /**
     *针对对Customer表的通用操作
     */
    public Customer queryForT_Customer(String sql,Object...args){
        try(PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql)){
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }
            ResultSet set = ps.executeQuery();
            /**通过获取结果集的源数据来获取查询后字段的列数*/
            ResultSetMetaData metaDate = set.getMetaData();
            /**获取结果集字段列数*/
            int columnCount = metaDate.getColumnCount();
            Customer customer = new Customer();
            while(set.next()){
                /**获取结果集中每一列的数据*/
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = set.getObject(i + 1);
                    /**获取每个字段的字段名*/
                    String columnName = metaDate.getColumnName(i + 1);
                    /**通过反射获取类在动态运行时的属性，并动态的为属性赋值*/
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,columnValue);
                }
            }
            System.out.println(customer);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
