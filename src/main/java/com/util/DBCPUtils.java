package com.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBCPUtils {

    private static DataSource factory = null;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            factory = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnByDBCP() throws Exception {
        Connection connection = factory.getConnection();
        return connection;
    }
}
