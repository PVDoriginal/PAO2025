package org.shelter;

import java.sql.*;

public class Main {

    static final String URL = "jdbc:postgresql://localhost/shelter?user=postgres&password=password";

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

//        String sql = "SELECT * FROM shelter";
//
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        ResultSet result = stmt.executeQuery();
//
//        while (result.next()) {
//            System.out.println(result.getString("name"));
//        }
        conn.setAutoCommit(false);

        String sql = """
                        INSERT INTO shelter(name, address)
                        VALUES(?, ?)""";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "Shelter");
        stmt.setString(2, "address...");
        stmt.executeUpdate();

        conn.commit();

        stmt.close();
        conn.close();
    }
}