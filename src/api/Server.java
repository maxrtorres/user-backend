package api;

import java.sql.Connection;
import java.sql.SQLException;
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
                    try {
                        User user = userDao.read(username);
                        if (user == null)  {
                            halt(404, "Not Found");
                            return null;
                        }
                        return mapper.writeValueAsString(user);
                    }
                    catch (DaoException e) {
                        halt(500, "Internal Server Error");
                        return null;
                    }
                });
            });
        });
    }
}