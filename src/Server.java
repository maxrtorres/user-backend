import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private static final String databaseUrl = System.getenv("DATABASE_URL");
    private static final String user = System.getenv("USER");
    private static final String password = System.getenv("PASSWORD");

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, user, password);
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM users");
            while (res.next()) {
                System.out.println("id: " + res.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}