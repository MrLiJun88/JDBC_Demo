package conn;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class MyConnTest {

    /**获取数据库连接方式一*/
    @Test
    public void testConn() throws Exception{
        /**创建数据库连接驱动*/
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test";
        /**将用户名与密码保存在Properties属性中*/
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","123");
        /**获取数据库连接*/
        Connection connection = driver.connect(url,properties);
        System.out.println(connection);
    }

    /**获取数据库连接方式二,对于方式一的迭代
     * 使用反射来实现,在代码中不出现第三方的api，使程序有比较好的移植性
     * */
    @Test
    public void testConn2() throws Exception{
        /**使用反射生成Driver实例*/
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();
        String url = "jdbc:mysql://localhost:3306/test";
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","123");
        /**获取数据库连接*/
        Connection connection = driver.connect(url,properties);
        System.out.println(connection);
    }

    /**
     * 获取数据库连接三
     * DriverManager:使用DriverManager来代替Manager
     */
    @Test
    public void testConn3() throws Exception{
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123";

        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }

    /**
     * 获取数据库连接四
     *
     *
     */
    @Test
    public void testConn4() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123";

        /**
         * 相较对方式三，可以省略实例化Driver和注册Driver操作
         * 因为在Driver加载中已经有Driver类中的静态代码块中代码帮我们完成了注册
         *
         * Class.forName("com.mysql.jdbc.Driver")也可以省略不写
         * 因为在mysql包中META-INF/service/java.sqlDriver中已经帮我们写好了驱动路径
         */
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }

    /**
     * 与数据库连接的最终版
     * 将与数据库连接的信息写入一个配置文件中，实现代码与数据的解耦
     * 读取配置文件获取与数据库的连接
     * @throws Exception
     */
    @Test
    public void testConn5() throws Exception {
        /**加载配置文件*/
        InputStream is = MyConnTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        /**加载驱动*/
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }
}
