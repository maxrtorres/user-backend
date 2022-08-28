package api;

import java.sql.Connection;
import java.sql.SQLException;
import exceptions.ApiException;
import exceptions.DaoException;
import static util.Database.getConnection;
import dao.UserDao;
import static spark.Spark.*;
import model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        UserDao userDao = new UserDao(conn);
        port(4567);
        ObjectMapper mapper = new ObjectMapper();

        path("/api", () -> {
            path("/users", () -> {
                get("/:username", (req, res) -> {
                    String username = req.params("username");
                    User user = userDao.read(username);
                    return mapper.writeValueAsString(user);
                });
            });
        });
    }
}