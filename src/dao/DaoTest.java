package dao;

import api.Server;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.Database;
import util.SampleData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {
    private static List<User> sampleUsers;
    private static Connection conn;
    private static UserDao userDao;

    @BeforeAll
    public static void startServer() throws SQLException {
        sampleUsers = SampleData.sampleUsers();
        sampleUsers.sort(Comparator.comparing(User::getUsername));
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
        List<User> daoUsers = userDao.readAll();
        daoUsers.sort(Comparator.comparing(User::getUsername));
        assertIterableEquals(sampleUsers, daoUsers);
    }

    @Test
    public void readUserByUsername() {
        for (User user : sampleUsers) {
            User daoUser = userDao.read(user.getUsername());
            assertEquals(user, daoUser);
        }
    }

    @Test
    public void readNonexistentUserFails() {
        User daoUser = userDao.read("idontexist");
        assertNull(daoUser);
    }

    @Test
    public void createUser() {
        User user = new User("new2","New User");
        assert(userDao.create(user.getUsername(),user.getFullName()));
        assertEquals(user, userDao.read(user.getUsername()));
    }

    @Test
    public void updateUser() {
        for (User user : sampleUsers) {
            assert(userDao.update(user.getUsername(),"New Name"));
            assertEquals("New Name",
                    userDao.read(user.getUsername()).getFullName());
        }
    }

    @Test
    public void deleteUser() {
        for (User user : sampleUsers) {
            assert(userDao.delete(user.getUsername()));
            assertNull(userDao.read(user.getUsername()));
        }
        assertEquals(0, userDao.readAll().size());
    }

    @Test
    public void deleteNonexistentUserFails() {
        assertFalse(userDao.delete("idontexist"));
    }
}
