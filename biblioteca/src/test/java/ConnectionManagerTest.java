import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.zervladpy.data.connection.h2.H2Connection;

public class ConnectionManagerTest {

    @Test
    public void testConnection() {
        try (Connection conn = H2Connection.getConnection()) {
            assertNotNull(conn);
            H2Connection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
