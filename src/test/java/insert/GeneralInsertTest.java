package insert;

import com.util.JDBCUtils;
import org.junit.Test;

import java.sql.PreparedStatement;

/**
 * 向customers表中插入数据
 */
public class GeneralInsertTest {

    @Test
    public void testInsertToCustomer() throws Exception{
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        this.insert(sql,"吴梦瑶","wmy@qq.com","1996-12-17");
    }

    public void insert(String sql,Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        /**
         * 如果execute()是一个查询操作，有结果集则返回 true
         * 如果执行是 增，删，改操作，则返回 false
         */
        ps.executeUpdate();
    }
}
