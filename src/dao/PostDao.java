package dao;

import exceptions.DaoException;
import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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