package batch;

import com.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 批量操作
 */
public class BatchOperTest {

    /**
     * 使用PreparedStatement批量插入
     */
    @Test
    public void testInsert1() throws Exception{
        String sql = "insert into t_person(name) values(?)";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            ps.setObject(1,"name_" + i);
            ps.execute();
        }
        JDBCUtils.closeConn();
        ps.close();
    }

    /*
     * 修改1： 使用 addBatch() / executeBatch() / clearBatch()
     * 修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
     * 		 ?rewriteBatchedStatements=true 写在配置文件的url后面
     */
    @Test
    public void testInsert2() throws Exception{
        String sql = "insert into t_person(name) values(?)";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i <= 1000000; i++) {
            ps.setObject(1,"name_" + i);
            ps.addBatch();
            if(0 == i % 500){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        JDBCUtils.closeConn();
        ps.close();
    }

    /**层次四：在层次三的基础上操作
     * 使用Connection 的 setAutoCommit(false)  /  commit()
     */
    @Test
    public void testInsert3() throws Exception{
        String sql = "insert into t_person(name) values(?)";
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        conn.setAutoCommit(false);
        /**设置不默认提交*/
        for (int i = 0; i <= 1000000; i++) {
            ps.setObject(1,"name_" + i);
            ps.addBatch();
            if(0 == i % 500){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        /**提交数据向数据库*/
        conn.commit();
        JDBCUtils.closeConn();
        ps.close();
    }
}
