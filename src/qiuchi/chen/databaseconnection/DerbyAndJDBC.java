package qiuchi.chen.databaseconnection;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

class DerbyAndJDBC {
    public static void main(String[] args) {
        //runTest();
        settingsOfStatement();
    }

    private static void runTest() {
        try (var conn = getConnection("jdbc:derby://localhost:1527/COREJAVA", "Issac", "123456");
             var statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE Greetings (MESSAGE_TEXT VARCHAR(20) )");
            statement.executeUpdate("INSERT INTO Greetings VALUES ('HELLO - WORLD!')");
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Greetings")) {
                while (resultSet.next()) {
                    var curCellValue = resultSet.getObject(1, String.class);
                    //resultSet.getString(1);<!>getXXX类方法如果类型不匹配，则会自动转换
                    System.out.println(curCellValue);
                }
            }
            var preparedStatement = conn.prepareStatement("SELECT * FROM Greetings WHERE MESSAGE_TEXT = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "HELLO - WORLD!");
            var result = preparedStatement.executeQuery();
            while (result.next()) {
                System.out.println(result.getString(1));
            }
            result.close();
            preparedStatement.close();

            statement.executeUpdate("DROP TABLE Greetings");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static void settingsOfStatement() {
        try (Connection connection = getConnection("jdbc:derby://localhost:1527/COREJAVA", "Issac", "123456");
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MyFavourite WHERE Duration = 'Infinite'");
            while (resultSet.relative(1)) {
                System.out.println(resultSet.getString("Name"));
                resultSet.updateString("Name", "1");
                resultSet.updateRow();//<!>updateString()更新的是结果集，updateRow()才提交给数据库
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static void CachedRowSet() throws SQLException {
        CachedRowSet cachedRowSet;
        try (var conn = getConnection("");
             var stat = conn.createStatement();
        ) {
            ResultSet resultSet = stat.executeQuery("");
            RowSetFactory factory = RowSetProvider.newFactory();
            cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.populate(resultSet);
        }//开链接，读到cache，关链接
        //或者：
        cachedRowSet.setUrl("");
        cachedRowSet.setUsername("");
        cachedRowSet.setPassword("");
        cachedRowSet.setCommand("SELECT * FROM ?");
        cachedRowSet.setString(1, "SomeTableName");
        cachedRowSet.setPageSize(10);
        cachedRowSet.nextPage();
    }

    private static void AboutMetaData() throws SQLException {
        Connection conn = getConnection("");
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        resultSetMetaData.getColumnLabel(1);
        resultSetMetaData.getColumnName(1);
    }
}
