package query;

import com.entity.Customer;
import com.util.JDBCUtils;
import org.junit.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 针对于Customer表的查询操作
 */
public class CustomerQueryTest {

    @Test
    public void testQuery(){
        String sql = "select * from customers where id=?";
        try(PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql)){
            ps.setObject(1,18);
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
                customer.setPhone(photo);
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
}
