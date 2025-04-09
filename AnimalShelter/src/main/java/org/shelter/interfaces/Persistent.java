package org.shelter.interfaces;

import org.javatuples.Pair;
import org.postgresql.core.Tuple;
import org.shelter.utility.Logs;

import java.sql.*;
import java.util.Optional;
import java.util.Vector;

public interface Persistent {
    String URL = "jdbc:postgresql://localhost/postgres?user=postgres&password=password";

    String tableName();
    Vector<Pair<String, String>> tableColumns();
    Vector<String> tableValues();
    int pk();

    default void createIfNotExists() {
        try {
            Connection conn = DriverManager.getConnection(URL);

            StringBuilder columns = new StringBuilder();
            for (Pair<String, String> column : tableColumns()) {
                columns.append(",\n").append(column.getValue0()).append(" ").append(column.getValue1());
            }

            String sql = String.format(
                    """
                            CREATE TABLE IF NOT EXISTS %s (
                                %s_ID INT PRIMARY KEY%s
                            )
                            """,
                    tableName(),
                    tableName(),
                    columns
            );

            PreparedStatement stmt = conn.prepareStatement(sql);

            Logs.log(stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            Logs.log(e.getMessage());
        }
    }

    default void insert() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            String sql = String.format(
                    """
                            INSERT INTO %s VALUES(%s%s)
                            """,
                    tableName(),
                    pk(),
                    new String(new char[tableValues().size()]).replace("\0", ", ?")
            );

            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < tableValues().size(); i++) {
                stmt.setString(i + 1, tableValues().get(i));
            }

            Logs.log(stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            Logs.log(e.getMessage());
        }
    }

    default void update() {
        try {
            Connection conn = DriverManager.getConnection(URL);

            StringBuilder names = new StringBuilder();
            for (Pair<String, String> column : tableColumns()) {
                names.append(column.getValue0()).append(" = ?").append(",\n");
            }
            names.deleteCharAt(names.length() - 2);

            String sql = String.format(
                    """
                            UPDATE %s SET
                            %s
                            WHERE %s_ID=%s
                            """,
                    tableName(),
                    names,
                    tableName(),
                    pk()
            );

            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < tableValues().size(); i++) {
                stmt.setString(i + 1, tableValues().get(i));
            }

            Logs.log(stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            Logs.log(e.getMessage());
        }
    }

    default void drop() {
        try {
            Connection conn = DriverManager.getConnection(URL);

            String sql = String.format(
                    """
                            DROP TABLE %s
                            """,
                    tableName()
            );

            PreparedStatement stmt = conn.prepareStatement(sql);
            Logs.log(stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            Logs.log(e.getMessage());
        }
    }

    void load(int pk);

    default Optional<Vector<String>> load_table(int pk) {
        try {
            Connection conn = DriverManager.getConnection(URL);

            String sql = String.format(
                    """
                        SELECT * FROM %s
                        WHERE %s_ID=%s
                        """,
                    tableName(),
                    tableName(),
                    pk
            );

            PreparedStatement stmt = conn.prepareStatement(sql);
            Logs.log(stmt.toString());
            ResultSet results = stmt.executeQuery();
            Vector<String> values = new Vector<>();

            if (results.next()) {
                for (Pair<String, String> column : tableColumns()) {
                    values.add(results.getString(column.getValue0()));
                }
            }

            stmt.close();
            conn.close();

            return Optional.of(values);
        }
        catch (SQLException e) {
            Logs.log(e.getMessage());
            return Optional.empty();
        }
    }
}
