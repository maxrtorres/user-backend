package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String databaseUrl = System.getenv("DATABASE_URL");
    private static final String user = System.getenv("USER");
    private static final String password = System.getenv("PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, user, password);
    }

    public static void main(String[] args) {
        try {
            Connection conn = util.Database.getConnection();
            createUsersTable(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        int res = statement.executeUpdate("DROP TABLE IF EXISTS users");
    }
}
