package api;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class ServerTest {

    private static Connection conn;

    @BeforeAll
    void startServer() throws SQLException {
        this.conn = Database.getSqlConnection();
        Server.main(null);
    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        Database.resetDatabase(conn);
    }

    @AfterAll
    void stopServer() throws SQLException {
        Server.stop();
        Database.resetDatabase(conn);
    }
}
