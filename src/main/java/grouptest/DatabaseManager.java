package grouptest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
public class DatabaseManager
{
    private static Connection connection;
    public static final String URL_DATABASE = "jdbc:mysql://localhost/unit_test_database";
    public static final String USER_DATABASE = "root";
    public static final String PASSWORD_DATABASE = "pocebleu";

    public static Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(URL_DATABASE, USER_DATABASE, PASSWORD_DATABASE);

            }
            return connection;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static void clearTable(String tableName) throws SQLException
    {
        String strQuery = "DELETE FROM $tableName;";
        String query = strQuery.replace("$tableName", tableName);
        Statement statement = getConnection().createStatement();

        statement.executeUpdate(query);

    }
}
