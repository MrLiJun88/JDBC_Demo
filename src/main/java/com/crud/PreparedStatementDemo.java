package com.crud;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 使用PreparedStatement代替Statement实现CRUD操作
 */
public class PreparedStatementDemo {

    @Test
    public void testInsert()throws Exception{
        // 1.加载配置文件
        InputStream is = PreparedStatementDemo.class.getClassLoader().getResourceAsStream("db.properties");
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
        Connection conn = DriverManager.getConnection(url, user, password);

        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        /**填充占位符*/
        ps.setString(1,"lijun");
        ps.setString(2,"lijun@qq.com");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("1996-12-17");
        ps.setDate(3,new java.sql.Date(date.getTime()));
        /**执行sql操作*/
        ps.execute();
        /**关闭流*/
        ps.close();
        conn.close();
    }
}
