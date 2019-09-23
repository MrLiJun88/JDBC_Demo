package com.crud;

import com.util.JDBCUtils;
import org.junit.Test;

import java.util.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

/**
 * 使用PreparedStatement代替Statement实现CRUD操作
 * PreparedStatement解决了Statement的拼串和sql注入问题
 * 而且可以处理Blob类型的数据
 * 更高效的批量插入
 */
public class PreparedStatementDemo {

    @Test
    public void testInsert()throws Exception{
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        /**填充占位符*/
        ps.setString(1,"wangwu");
        ps.setString(2,"wangwu@qq.com");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("1999-12-17");
        ps.setDate(3,new java.sql.Date(date.getTime()));
        /**执行sql操作*/
        ps.execute();
        /**关闭流*/
        ps.close();
        JDBCUtils.closeConn();
    }

    @Test
    public void testUpdate(){
        String sql = "update user set name=? where id=?";
        this.alter(sql,"吴梦瑶",5);
    }

    @Test
    public void testDelete(){
        String sql = "delete from customers where id=?";
        this.alter(sql,19);
    }

    /**
     * 通用的增删改操作方法
     */
    public void alter(String sql,Object... args){
        try(PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql)){
            /**sql中的占位符与传入可变参数的个数一致*/
            int length = args.length;
            /**填充占位符*/
            for (int i = 0; i < length; i++) {
                ps.setObject(i + 1,args[i]);
            }
            /**执行sql*/
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /**关闭资源*/
        finally {
            try{
                JDBCUtils.closeConn();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
