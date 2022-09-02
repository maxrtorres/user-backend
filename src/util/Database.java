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
        List<User> users = SampleData.sampleUsers();
        List<Post> posts = SampleData.samplePosts();
        resetDatabase(conn, users, posts);
    }

    public static void resetDatabase(Connection conn, List<User> users,
                                     List<Post> posts) throws SQLException {
        createUserTable(conn, users);
        createPostTable(conn, posts);
    }

    private static void createUserTable(Connection conn, List<User> users) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS post");
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        String sql = "CREATE TABLE user("
                + "username VARCHAR(50) NOT NULL PRIMARY KEY,"
                + "full_name VARCHAR(50) NOT NULL"
                + ");";
        statement.executeUpdate(sql);
        for (User user : users) {
            sql = "INSERT INTO user VALUES(\"%s\", \"%s\");";
            statement.executeUpdate(String.format(sql,
                    user.getUsername(), user.getFullName()));
        }
    }

    private static void createPostTable(Connection conn, List<Post> posts) throws SQLException {
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
        for (Post post : posts) {
            sql = "INSERT INTO post VALUES(\"%s\", \"%s\", \"%s\", \"%s\");";
            statement.executeUpdate(String.format(sql,
                    post.getId(), post.getAuthor(),
                    post.getTimePosted(), post.getContent()));
        }
    }
}
