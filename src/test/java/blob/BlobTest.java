package blob;

import com.util.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 向数据库中插入二进制文件
 */
public class BlobTest {

    @Test
    public void testInsertBlob() throws Exception{
        String sql = "insert into customers(name,email,birth,photo) value (?,?,?,?)";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        ps.setObject(1,"王五");
        ps.setObject(2,"wangwu@199.com");
        ps.setObject(3,"2019-9-6");
        /**向数据库中插入图片*/
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/lj.jpg")));
        ps.setBlob(4,bis);
        ps.execute();
        JDBCUtils.closeConn();
        ps.close();
    }

    /**
     * 从数据库查询出二进制文件
     * @throws Exception
     */
    @Test
    public void testQuery() throws Exception{
        String sql = "select name,email,birth birthday,photo from customers where id=?";
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        ps.setObject(1,21);
        ResultSet set = ps.executeQuery();
        while(set.next()){
            System.out.println(set.getObject(1));
            System.out.println(set.getObject(2));
            System.out.println(set.getObject(3));
            /**从数据库中将图片以二进制读出来*/
            Blob blob = set.getBlob(4);
            InputStream is = blob.getBinaryStream();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("lijun.jpg")));
            byte[] bytes = new byte[1024];
            int length;
            while(-1 != (length=is.read(bytes))){
                bos.write(bytes,0,length);
            }
            bos.flush();
            is.close();
            bos.close();
        }
        JDBCUtils.closeConn();
        ps.close();
    }
}
