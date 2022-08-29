package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import exceptions.DaoException;
import model.User;
import spark.Route;
import java.util.List;
import static spark.Spark.halt;

public class UserApi {
    public static UserDao userDao;
    public static ObjectMapper mapper;

    public static Route getAllUsers = (req, res) -> {
        try {
            List<User> users = userDao.readAll();
            return mapper.writeValueAsString(users);
        }
        catch (DaoException e) {
            halt(500,"Internal Server Error");
            return "";
        }
    };

    public static Route getUser = (req, res) -> {
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
    };

    public static Route postUser = (req, res) -> {
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
    };

    public static Route putUser = (req, res) -> {
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
    };

    public static Route deleteUser = (req, res) -> {
        String username = req.params("username");
        try {
            if (!userDao.delete(username)) {
                halt(404, "Not Found");
                return "";
            }
            return "";
        } catch (DaoException e) {
            halt(500, "Internal Server Error");
            return "";
        }
    };

}
