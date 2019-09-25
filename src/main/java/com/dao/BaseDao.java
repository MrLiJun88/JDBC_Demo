package com.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {

    private Class<T> clazz = null;
    {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)type;
        /**获取父类泛型参数*/
        Type[] t  = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>)t[0];
    }
    /**
     * 针对任一张表进行查询的操作
     */
    public  T getInstance(Connection connection,String sql, Object... args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(set.next()){
            /**通过反射，获取类的实体化对象*/
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = set.getObject(i + 1);
                /**getColumnLabel 获取列的别名*/
                String columnName = metaData.getColumnLabel(i + 1);
                Field field = t.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            return t;
        }
        ps.close();
        set.close();
        return null;
    }

    public <T> List<T> getForList(Connection connection,Class<T> clazz, String sql, Object... args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            /**为占位符设置参数*/
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<T> list = new ArrayList<>();
        while(set.next()){
            /**通过反射，获取类的实体化对象*/
            T t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object columnValue = set.getObject(i + 1);
                /**getColumnLabel 获取列的别名*/
                String columnName = metaData.getColumnLabel(i + 1);
                Field field = t.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(t,columnValue);
            }
            list.add(t);
        }
        ps.close();
        set.close();
        return list;
    }

    public void update(Connection connection,String sql,Object... args) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        /**
         * 如果execute()是一个查询操作，有结果集则返回 true
         * 如果执行是 增，删，改操作，则返回 false
         */
        ps.execute();
        ps.close();
    }

    public <T> T getValue(Connection connection,String sql) throws Exception{
        PreparedStatement ps = connection.prepareStatement(sql);
        /**
         * 如果execute()是一个查询操作，有结果集则返回 true
         * 如果执行是 增，删，改操作，则返回 false
         */
         ResultSet set = ps.executeQuery();
         while(set.next()){
             return (T) set.getObject(1);
         }
         ps.close();
         set.close();
         return null;
    }
}
