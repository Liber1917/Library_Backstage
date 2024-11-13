package JavaBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Closed connection successfully.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
