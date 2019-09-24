package insert;

import com.util.JDBCUtils;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class CRUDForExamStudent {

    @Test
    public void testQuery() throws Exception {
        String sql = "select Type,IDCard,StudentName,Location from examstudent where FlowId=?";
        this.query(sql, 1);
    }

    @Test
    public void testDelete() throws Exception{
        String sql = "delete from examstudent where FlowID=?";
        this.delete(sql,1);
    }

    /**向examstudent表查询数据*/
    public void query(String sql,Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ResultSet set = ps.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(set.next()){
            for (int i = 0; i < columnCount; i++) {
                System.out.println(set.getObject(i + 1));
            }
        }
    }

    /**向examstudent表删除数据*/
    public void delete(String sql,Object... args) throws Exception{
        PreparedStatement ps = JDBCUtils.getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        ps.execute();
    }
}
