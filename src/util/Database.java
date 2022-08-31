package util;

import model.Post;
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

    public static Connection getSqlConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, user, password);
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = getSqlConnection();
        resetDatabase(conn);
    }

    public static void resetDatabase(Connection conn) throws SQLException {
        createUserTable(conn);
        createPostTable(conn);
    }

    private static void createUserTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS post");
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

    private static void createPostTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS post");
        String sql = "CREATE TABLE post("
                + "id VARCHAR(50) NOT NULL PRIMARY KEY,"
                + "author VARCHAR(50) NOT NULL,"
                + "time_posted DATETIME NOT NULL,"
                + "content VARCHAR(280) NOT NULL,"
                + "FOREIGN KEY (author) REFERENCES user(username) ON DELETE CASCADE"
                + ");";
        statement.executeUpdate(sql);
        List<Post> posts = SampleData.samplePosts();
        for (Post post : posts) {
            sql = "INSERT INTO post VALUES(\"%s\", \"%s\", \"%s\", \"%s\");";
            statement.executeUpdate(String.format(sql,
                    post.getId(), post.getAuthor(),
                    post.getTimePosted(), post.getContent()));
        }
    }
}
