package api;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.Database;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private static Connection conn;
    private final String BASE_URL = "http://localhost:4567/api";

    @BeforeAll
    public static void startServer() throws SQLException {
        conn = Database.getSqlConnection();
        Server.main(null);
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Database.resetDatabase(conn);
    }

    @AfterAll
    public static void stopServer() throws SQLException {
        Server.stopServer();
        Database.resetDatabase(conn);
    }

    @Test
    public void getAllUsers() {
        String url = BASE_URL + "/users";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(3, res.getBody().getArray().length());
    }
}
