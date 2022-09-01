package dao;

import java.sql.Connection;

public class PostDao {
    private final Connection conn;

    public PostDao(Connection conn) {
        this.conn = conn;
    }

}
