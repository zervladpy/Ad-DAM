package connection;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.zervladpy.data.connection.ConnectionManager;
import com.zervladpy.data.connection.h2.H2Connection;

public class ConnectionManagerTest {

    private ConnectionManager connectionManager = H2Connection.getInstance();

    @Test
    public void testOpenConnection() {

        Connection conn = connectionManager.getConnection();

        try {
            assertFalse(conn.isValid(0));
        } catch (SQLException e) {
            fail(e.getMessage());
        }

        connectionManager.closeConnection();

    }

    @Test
    public void testCloseConnection() {

        connectionManager.closeConnection();

        Connection conn = connectionManager.getConnection();

        try {
            assertFalse(conn.isValid(0));
        } catch (SQLException e) {
            fail(e.getMessage());
        }

    }

}
