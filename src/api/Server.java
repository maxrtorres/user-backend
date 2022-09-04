package api;

import java.sql.Connection;
import java.sql.SQLException;
import static util.Database.getSqlConnection;
import dao.UserDao;
import dao.PostDao;
import static spark.Spark.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getSqlConnection();
        UserDao userDao = new UserDao(conn);
        PostDao postDao = new PostDao(conn);
        port(4567);
        ObjectMapper mapper = new ObjectMapper();
        UserApi.userDao = userDao;
        UserApi.mapper = mapper;
        PostApi.postDao = postDao;
        PostApi.mapper = mapper;

        path("/api", () -> {
                path("/users", () -> {
                    get("", UserApi.getAllUsers);
                    get("/:username", UserApi.getUser);
                    post("", UserApi.postUser);
                    put("/:username", UserApi.putUser);
                    delete("/:username", UserApi.deleteUser);
                });
                path("/posts", () -> {
                    get("", PostApi.getAllPosts);
                    get("/:id", PostApi.getPost);
                    post("", PostApi.postPost);
                    put("/:id", PostApi.putPost);
                    delete("/:id", PostApi.deletePost);
                });
        });
    }

    public static void stopServer() {
        stop();
    }
}