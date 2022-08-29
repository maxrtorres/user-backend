package api;

import java.sql.Connection;
import java.sql.SQLException;
import static util.Database.getConnection;
import dao.UserDao;
import static spark.Spark.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        UserDao userDao = new UserDao(conn);
        port(4567);
        ObjectMapper mapper = new ObjectMapper();
        UserApi.userDao = userDao;
        UserApi.mapper = mapper;

        path("/api", () ->
            path("/users", () -> {
                get("", UserApi.getAllUsers);
                get("/:username", UserApi.getUser);
                post("", UserApi.postUser);
                put("/:username", UserApi.putUser);
                delete("/:username", UserApi.deleteUser);
            })
        );
    }
}