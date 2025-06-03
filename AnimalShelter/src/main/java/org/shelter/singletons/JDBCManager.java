package org.shelter.singletons;

import org.shelter.interfaces.Persistent;
import org.shelter.utility.Logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

public final class JDBCManager {

    static final String URL = "jdbc:postgresql://localhost/postgres?user=postgres&password=password";
    private static JDBCManager INSTANCE;

    private final Connection conn;

    private JDBCManager() {
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public static JDBCManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCManager();
        }
        return INSTANCE;
    }

    public Boolean checkIfTableExists(String tableName) {
        Logs.log("Checking if table " + tableName + " exists");
        try {
            PreparedStatement stmt = conn.prepareStatement(String.format(
                    """
                            SELECT * FROM %s;
                            """,
                    tableName
            ));
            Logs.log(stmt.toString());

            stmt.executeQuery();
            Logs.log("it does");

            stmt.close();
            conn.close();

            return true;
        }
        catch (SQLException e) {
            Logs.log("it doesn't");
            return false;
        }
    }
}
