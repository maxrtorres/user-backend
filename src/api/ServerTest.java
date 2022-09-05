package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.Unirest;
import model.Post;
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
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.SampleData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServerTest {

    private static Connection conn;
    private final String BASE_URL = "http://localhost:4567/api";
    private final ObjectMapper mapper = new ObjectMapper();
    private static List<User> sampleUsers;
    private static List<Post> samplePosts;

    @BeforeAll
    public static void startServer() throws SQLException {
        conn = Database.getSqlConnection();
        sampleUsers = SampleData.sampleUsers();
        samplePosts = SampleData.samplePosts();
        Server.main(null);
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Database.resetDatabase(conn, sampleUsers, samplePosts);
    }

    @AfterAll
    public static void stopServer() throws SQLException {
        Server.stopServer();
        Database.resetDatabase(conn, sampleUsers, samplePosts);
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
        assertNull(res.getBody());
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
        assertNull(res.getBody());
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
        assertNull(res.getBody());
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
        assertNull(res.getBody());
    }

    @Test
    public void getAllPosts() {
        String url = BASE_URL + "/posts";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(9, res.getBody().getArray().length());
    }

    @Test
    public void getAllPostsByAuthor() {
        String url = BASE_URL + "/posts?author=alee3";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(3, res.getBody().getArray().length());
    }

    @Test
    public void getPostById() {
        String url = BASE_URL + "/posts/" + samplePosts.get(0).getId();
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void getNonexistentPostsFails() {
        String url = BASE_URL + "/posts/idontexist";
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        assertEquals(404, res.getStatus());
        assertNull(res.getBody());
    }

    @Test
    public void postPost() throws JsonProcessingException {
        String url = BASE_URL + "/posts";
        Post post = new Post("alee3",
                "2020-01-01 00:00:00","Hello World");
        HttpResponse<JsonNode> res = Unirest.post(url)
                .body(mapper.writeValueAsString(post)).asJson();
        assertEquals(201, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void postPostNoBodyFails() {
        String url = BASE_URL + "/posts";
        HttpResponse<JsonNode> res = Unirest.post(url).asJson();
        assertEquals(400, res.getStatus());
        assertNull(res.getBody());
    }

    @Test
    public void putPost() throws JsonProcessingException {
        String url = BASE_URL + "/posts/" + samplePosts.get(0).getId();
        HttpResponse<JsonNode> res = Unirest.put(url)
                .body(mapper.writeValueAsString(samplePosts.get(0))).asJson();
        assertEquals(200, res.getStatus());
        assertEquals(1, res.getBody().getArray().length());
    }

    @Test
    public void putPostNoBodyFails() {
        String url = BASE_URL + "/posts/" + samplePosts.get(0).getId();
        HttpResponse<JsonNode> res = Unirest.put(url).asJson();
        assertEquals(400, res.getStatus());
        assertNull(res.getBody());
    }
}
