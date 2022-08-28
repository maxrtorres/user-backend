package api;

import java.sql.Connection;
import java.sql.SQLException;
import exceptions.DaoException;
import static util.Database.getConnection;
import dao.UserDao;
import static spark.Spark.*;
import model.User;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        try {
            UserDao userDao = new UserDao(conn);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        get("/hello", (req, res) -> {
            return "Hello World";
        });
    }
}