package dao;

import api.Server;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.Database;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DaoTest {

    private static Connection conn;
    private static UserDao userDao;

    @BeforeAll
    public static void startServer() throws SQLException {
        conn = Database.getSqlConnection();
        userDao = new UserDao(conn);
        Server.main(null);
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Database.resetDatabase(conn);
    }

    @AfterAll
    public static void stopServer() throws SQLException {
        Server.stopServer();
        Database.resetDatabase(conn);
    }

    @Test
    public void readAllUsers() {

    }

    @Test
    public void readUserByUsername() {

    }

    @Test
    public void readNonexistentUserFails() {

    }

    @Test
    public void createUser() {

    }

    @Test
    public void updateUser() {

    }

    @Test
    public void deleteUser() {

    }

    @Test
    public void deleteNonexistentUserFails() {

    }
}
