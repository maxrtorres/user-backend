package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.DaoException;
import model.User;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public boolean create(User user) {
        try {
            String sql = "INSERT INTO user VALUES(\"%s\", \"%s\");";
            Statement statement = conn.createStatement();
            int res = statement.executeUpdate(String.format(sql,
                    user.getUsername(), user.getFullName()));
            return res == 1;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public User read(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username=\"%s\";";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format(sql,username));
            List<User> users = getUserList(rs);
            if (users.size() > 1) throw new SQLException();
            if (users.size() == 0) return null;
            return users.get(0);
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<User> readAll() {
        try {
            String sql = "SELECT * FROM user;";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<User> users = getUserList(rs);
            if (users.size() == 0) return null;
            return users;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private List<User> getUserList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getString("username"),
                    rs.getString("full_name")));
        }
        return users;
    }
}
