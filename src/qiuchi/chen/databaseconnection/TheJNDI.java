package qiuchi.chen.databaseconnection;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class TheJNDI {
    public static void main(String[] args) throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("");
        Connection conn = dataSource.getConnection();
        //从服务器配置文件中寻找数据源配置来建立链接
    }
}
