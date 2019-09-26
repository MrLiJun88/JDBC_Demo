package com.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class C3p0ConnUtils {
    /**从c3p0配置文件中获取与数据库连接*/
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

    public static Connection getConn() throws Exception {
        /**获取c3p0数据库连接池*/
        Connection connection = cpds.getConnection();
        return connection;
    }

}
