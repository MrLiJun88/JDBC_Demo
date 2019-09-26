package pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidConnPool {

    @Test
    public void getConn() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123");

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /**通过配置文件获取Druid与数据库连接*/
    @Test
    public void testTetConnByProperties() throws Exception{
        InputStream is = ClassLoader.getSystemResourceAsStream("druid.properties");
        Properties properties = new Properties();
        properties.load(is);
        DataSource datasource =  DruidDataSourceFactory.createDataSource(properties);
        System.out.println(datasource.getConnection());
    }
}
