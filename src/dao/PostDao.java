package dao;

import exceptions.DaoException;
import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostDao {
    private final Connection conn;

    public PostDao(Connection conn) {
        this.conn = conn;
    }

    public List<Post> readAll() throws DaoException {
        try {
            String sql = "SELECT * FROM post;";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return getPostList(rs);
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<Post> readAllByAuthor(String author) throws DaoException {
        try {
            String sql = "SELECT * FROM post WHERE author=\"%s\";";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format(sql,author));
            return getPostList(rs);
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Post read(String id) throws DaoException {
        try {
            String sql = "SELECT * FROM post WHERE id=\"%s\";";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format(sql,id));
            List<Post> posts = getPostList(rs);
            if (posts.size() > 1) throw new SQLException();
            if (posts.size() == 0) return null;
            return posts.get(0);
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public boolean create(String author, String timePosted, String content) throws DaoException {
        try {
            String sql = "INSERT INTO post VALUES(\"%s\", \"%s\", \"%s\", \"%s\");";
            Statement statement = conn.createStatement();
            int res = statement.executeUpdate(String.format(sql,
                    UUID.randomUUID(), author, timePosted, content));
            return res == 1;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public boolean update(String id, String content) throws DaoException {
        try {
            String sql = "UPDATE post SET content = \"%s\" WHERE id = \"%s\";";
            Statement statement = conn.createStatement();
            int res = statement.executeUpdate(String.format(sql,
                    content, id));
            return res == 1;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public boolean delete(String id) throws DaoException {
        try {
            String sql = "DELETE FROM post WHERE id = \"%s\";";
            Statement statement = conn.createStatement();
            int res = statement.executeUpdate(String.format(sql, id));
            return res == 1;
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private List<Post> getPostList(ResultSet rs) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Post(rs.getString("id"),
                    rs.getString("author"),
                    rs.getString("time_posted"),
                    rs.getString("content")));
        }
        return posts;
    }

}
