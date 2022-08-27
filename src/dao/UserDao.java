package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.DaoException;
import model.User;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public boolean create(String username, String fullName) {
        try {
            String sql = "INSERT INTO user VALUES(\"%s\", \"%s\");";
            Statement statement = conn.createStatement();
            int res = statement.executeUpdate(String.format(sql,
                    username, fullName));
            return res == 1;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public User read(String username) {
        return null;
    }
}
