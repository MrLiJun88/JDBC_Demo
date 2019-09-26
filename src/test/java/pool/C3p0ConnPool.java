package pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.util.C3p0ConnUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * 使用c3p0连接池
 */
public class C3p0ConnPool {

    @Test
    public void testConnByC3p0() throws Exception {
        /**获取c3p0数据库连接池*/
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("123");

        /**设置初始池中数据库连接数*/
        dataSource.setInitialPoolSize(5);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testConnByC3p02() throws Exception {
        System.out.println(C3p0ConnUtils.getConn());
    }
}
