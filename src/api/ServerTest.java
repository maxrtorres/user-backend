package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.Unirest;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.Database;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    private static Connection conn;
    private final String BASE_URL = "http://localhost:4567/api";
    private final ObjectMapper mapper = new ObjectMapper();

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

    @Test
    public void getUserByUsername() {
        String url = BASE_URL + "/users/alee3";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void getNonexistentUserFails() {
        String url = BASE_URL + "/users/idontexist";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(404, res.getStatus());
        assertEquals(null, res.getBody());
    }

    @Test
    public void postUser() throws JsonProcessingException {
        String url = BASE_URL + "/users";
        User user = new User("jsmith1","James Smith");
        HttpResponse<JsonNode> res = Unirest.post(url)
                .body(mapper.writeValueAsString(user)).asJson();
        assertEquals(201, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void postUserNoBodyFails() {
        String url = BASE_URL + "/users";
        HttpResponse<JsonNode> res = Unirest.post(url).asJson();
        assertEquals(400, res.getStatus());
        assertEquals(null, res.getBody());
    }

    @Test
    public void putUser() throws JsonProcessingException {
        String url = BASE_URL + "/users/alee3";
        User user = new User("alee3","Alexander Lee");
        HttpResponse<JsonNode> res = Unirest.put(url)
                .body(mapper.writeValueAsString(user)).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void putUserNoBodyFails() {
        String url = BASE_URL + "/users/alee3";
        HttpResponse<JsonNode> res = Unirest.put(url).asJson();
        assertEquals(400, res.getStatus());
        assertEquals(null, res.getBody());
    }

    @Test
    public void deleteUser() {
        String url = BASE_URL + "/users/alee3";
        HttpResponse<JsonNode> res = Unirest.delete(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void deleteNonexistentUserFails() {
        String url = BASE_URL + "/users/idontexist";
        HttpResponse<JsonNode> res = Unirest.delete(url).asJson();
        assertEquals(404, res.getStatus());
        assertEquals(null, res.getBody());
    }
}
