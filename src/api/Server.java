package api;

import java.sql.Connection;
import java.sql.SQLException;

import exceptions.DaoException;
import static util.Database.getConnection;
import dao.UserDao;
import model.User;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        try {
            UserDao userDao = new UserDao(conn);
            System.out.print(userDao.read("jsmith30"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}