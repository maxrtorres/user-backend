package api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
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
                get("", (req, res) -> {
                    try {
                        List<User> users = userDao.readAll();
                        return mapper.writeValueAsString(users);
                    }
                    catch (DaoException e) {
                        halt(500,"Internal Server Error");
                        return "";
                    }
                });
                get("/:username", (req, res) -> {
                    String username = req.params("username");
                    try {
                        User user = userDao.read(username);
                        if (user == null)  {
                            halt(404, "Not Found");
                            return "";
                        }
                        return mapper.writeValueAsString(user);
                    }
                    catch (DaoException e) {
                        halt(500, "Internal Server Error");
                        return "";
                    }
                });
                post("", (req, res) -> {
                    try {
                        User user = mapper.readValue(req.body(), User.class);
                        userDao.create(user.getUsername(), user.getFullName());
                        res.status(201);
                        return mapper.writeValueAsString(user);
                    }
                    catch (DaoException e) {
                        halt(500, "Internal Server Error");
                        return "";
                    }
                    catch (Exception e) {
                        halt(400, "Bad Request");
                        return "";
                    }
                });
                put("/:username", (req, res) -> {
                    String username = req.params("username");
                    try {
                        User user = mapper.readValue(req.body(), User.class);
                        if (!username.equals(user.getUsername())) {
                            halt(400, "Bad Request");
                            return "";
                        }
                        userDao.update(username, user.getFullName());
                        return mapper.writeValueAsString(user);
                    }
                    catch (DaoException e) {
                        halt(500, "Internal Server Error");
                        return "";
                    }
                    catch (Exception e) {
                        halt(400, "Bad Request");
                        return "";
                    }
                });
                delete("/:username", (req, res) -> {
                    String username = req.params("username");
                    try {
                        if (!userDao.delete(username)) {
                            halt(404, "Not Found");
                            return "";
                        }
                        return "";
                    }
                    catch (DaoException e) {
                        halt(500, "Internal Server Error");
                        return "";
                    }
                });
            });
        });
    }
}