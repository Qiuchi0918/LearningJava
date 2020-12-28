package qiuchi.chen.databaseconnection;

import org.apache.derby.iapi.services.cache.CacheFactory;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

class TheTransaction {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection("jdbc:derby://localhost:1527/");
        conn.setAutoCommit(false);//<!>若自动提交则不能回滚
        Statement stat = conn.createStatement();

        stat.execute("");
        stat.execute("");

        conn.commit();
        conn.rollback();//二选一
    }

    private static void StandardPractise() throws SQLException {
        Connection conn = getConnection("");
        conn.setAutoCommit(false);
        Savepoint savepoint = conn.setSavepoint();
        try (Statement stat = conn.createStatement()) {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            stat.execute("");
            cachedRowSet.populate(stat.executeQuery(""));
        } catch (SQLException sqlException) {
            conn.rollback(savepoint);
            sqlException.printStackTrace();
        } finally {//其实应该条件判断是否commit()
            conn.releaseSavepoint(savepoint);
            conn.close();
        }
    }

    private static void AboutBatchUpdate() throws SQLException {
        Connection conn = null;
        conn.getAutoCommit();//<^>一般储存一个原有模式，事务完成后还原

        Statement statement = null;
        statement.addBatch("");//<^>这种方法不能使用preparedstatement
        statement.addBatch("");
        int[] resultArr = statement.executeBatch();
    }
}
