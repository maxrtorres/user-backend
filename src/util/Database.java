package util;

import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            createUserTable(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUserTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        String sql = "CREATE TABLE user("
                + "username VARCHAR(50) NOT NULL PRIMARY KEY,"
                + "full_name VARCHAR(50) NOT NULL"
                + ");";
        statement.executeUpdate(sql);
        List<User> users = SampleData.sampleUsers();
        for (User user : users) {
            sql = "INSERT INTO user VALUES(\"%s\", \"%s\");";
            statement.executeUpdate(String.format(sql,
                    user.getUsername(), user.getFullName()));
        }
    }
}
