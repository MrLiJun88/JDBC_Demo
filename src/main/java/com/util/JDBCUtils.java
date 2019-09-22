package com.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCUtils {

    static Connection conn = null;
    public static Connection getConnection() throws Exception{
        // 1.加载配置文件
        InputStream is = ClassLoader.getSystemResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(is);

        // 2.读取配置信息
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");

        // 3.加载驱动
        Class.forName(driver);

        // 4.获取连接
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void closeConn() throws Exception{
        conn.close();
    }
}
