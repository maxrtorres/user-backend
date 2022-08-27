package dao;

import java.sql.Connection;
import model.User;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public User create(String username, String fullName) {
        User user = new User(username, fullName);
        return user;
    }

    public User read(String username) {
        return null;
    }
}
