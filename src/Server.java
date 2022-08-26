import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static util.Database.getConnection;

public class Server {
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}