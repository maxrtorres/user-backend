package dao;

import api.Server;
import model.Post;
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
    private static List<Post> samplePosts;
    private static Connection conn;
    private static UserDao userDao;
    private static PostDao postDao;

    @BeforeAll
    public static void startServer() throws SQLException {
        sampleUsers = SampleData.sampleUsers();
        sampleUsers.sort(Comparator.comparing(User::getUsername));
        samplePosts = SampleData.samplePosts();
        samplePosts.sort(Comparator.comparing(Post::getId));
        conn = Database.getSqlConnection();
        userDao = new UserDao(conn);
        postDao = new PostDao(conn);
        Server.main(null);
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Database.resetDatabase(conn, sampleUsers, samplePosts);
    }

    @AfterAll
    public static void stopServer() throws SQLException {
        Server.stopServer();
        Database.resetDatabase(conn, sampleUsers, samplePosts);
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
            String username = user.getUsername();
            assert(userDao.delete(username));
            assertNull(userDao.read(username));
            assertEquals(0, postDao.readAllByAuthor(username).size());
        }
        assertEquals(0, userDao.readAll().size());
    }

    @Test
    public void deleteNonexistentUserFails() {
        assertFalse(userDao.delete("idontexist"));
    }

    @Test
    public void readAllPosts() {
        List<Post> daoPosts = postDao.readAll();
        daoPosts.sort(Comparator.comparing(Post::getId));
        assertIterableEquals(samplePosts, daoPosts);
    }

    @Test
    public void readAllPostsByAuthor() {
        List<Post> daoPosts = postDao.readAllByAuthor("alee3");
        assertEquals(3, daoPosts.size());
        for (Post post : daoPosts) {
            assertTrue(samplePosts.contains(post));
        }
    }

    @Test
    public void readPostById() {
        for (Post post : samplePosts) {
            Post daoPost = postDao.read(post.getId());
            assertEquals(post, daoPost);
        }
    }

    @Test
    public void readNonexistentPostFails() {
        Post daoPost = postDao.read("idontexist");
        assertNull(daoPost);
    }
}
