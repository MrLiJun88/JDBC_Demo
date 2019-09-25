package transcation;

import com.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * JDBC对事务的操作
 * 事务：一组逻辑操作单元,使数据从一种状态变换到另一种状态。
 * 事务处理（事务操作）：保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式。
 * 当在一个事务中执行多个操作时，要么所有的事务都**被提交(commit)
 * 那么这些修改就永久地保存下来；要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)到最初状态。
 * 为确保数据库中数据的一致性，数据的操纵应当是离散的成组的逻辑单元：
 * 当它全部完成时，数据的一致性可以保持，而当这个单元中的一部分操作失败，整个事务应全部视为错误，
 * 所有从起始点以后的操作应全部回退到开始状态。
 *
 * - 数据什么时候意味着提交？
 *   - 当一个连接对象被创建时，默认情况下是自动提交事务：
 *   每次执行一个 SQL 语句时，如果执行成功，就会向数据库自动提交，而不能回滚。
 *   - 关闭数据库连接，数据就会自动的提交。如果多个操作，每个操作使用的是自己单独的连接，则无法保证事务。
 *   即同一个事务的多个操作必须在同一个连接下。
 * - JDBC程序中为了让多个 SQL 语句作为一个事务执行：
 *   - 调用 Connection 对象的 **setAutoCommit(false); 以取消自动提交事务
 *   - 在所有的 SQL 语句都成功执行后，调用 commit();方法提交事务
 *   - 在出现异常时，调用 rollback(); 方法回滚事务
 */
public class MyTransactionTest {

    @Test
    public void testUpdate() {
        Connection connection = null;
        try{
            connection = JDBCUtils.getConnection();
            /**取消数据的自动提交*/
            connection.setAutoCommit(false);
            String sql = "update user_table set balance=balance-100 where user=?";
            this.updateWithTransaction(connection,sql,"AA");
            String sql2 = "update user_table set balance=balance+100 where user=?";
            this.updateWithTransaction(connection,sql2,"BB");
            /**提交数据*/
            connection.commit();
        }
        catch (Exception e){
            /**当数据发生异常时，立即回滚*/
            try{
                connection.rollback();
            }
            catch (Exception e1){
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try{
                /**关闭连接之前，恢复自动提交*/
                connection.setAutoCommit(true);
                JDBCUtils.closeConn();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**处理事务的更新操作*/
    public void updateWithTransaction(Connection connection,String sql, Object... args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ps.execute();
        ps.close();
    }


}
