package pool;

import com.util.DBCPUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBCPConnPool {

    /**
     * 获取DBCP数据库连接池
     * @throws Exception
     */
    @Test
    public void testConnByDbcp() throws Exception{
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123");

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /**通过dbcp配置文件获取与数据库的连接*/
    @Test
    public void testGetConnByDbcpFromProperties() throws Exception{
        Connection connection = DBCPUtils.getConnByDBCP();
        System.out.println(connection);
    }
}
