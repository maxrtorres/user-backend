package api;

import java.sql.Connection;
import java.sql.SQLException;

import exceptions.DaoException;
import static util.Database.getConnection;
import dao.UserDao;

public class Server {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        try {
            UserDao userDao = new UserDao(conn);
            userDao.create("3","4");
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}