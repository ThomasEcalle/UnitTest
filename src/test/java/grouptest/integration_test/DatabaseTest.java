package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.User;
import grouptest.UserDatabaseService;
import org.assertj.core.api.Assertions;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
public class DatabaseTest
{
    private static Connection connection;

    // User creation
    protected User mainUser = new User.Builder()
            .pseudo("sanchyu")
            .email("thomas@hotmail.fr")
            .password("toto")
            .build();


    protected UserDatabaseService userDatabaseService = new UserDatabaseService();


    @BeforeClass
    public static void initConnection() throws SQLException
    {
        connection = DatabaseManager.getConnection();
    }

    @Before
    public void beforeClean() throws SQLException
    {
        DatabaseManager.clearTable("user");
    }


    @Test
    public void should_connect_to_database() throws SQLException
    {
        final PreparedStatement statement = connection.prepareStatement
                ("insert into user (pseudo, password, email) VALUES ('Tom', 'azerty', 'thomas@htomail.fr')");


        final int insertedRows = statement.executeUpdate();

        Assertions.assertThat(insertedRows).isEqualTo(1);
    }

    @Test
    public void should_insert_user() throws SQLException
    {
        userDatabaseService.insertUser(mainUser);

        final PreparedStatement statement = connection.prepareStatement
                ("Select * from user");

        final ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Assertions.assertThat(resultSet.getRow()).isEqualTo(1);
    }


    @After
    public void afterClean() throws SQLException
    {
        DatabaseManager.clearTable("user");
    }

    @AfterClass
    public static void closeConnection() throws SQLException
    {
        connection.close();
    }
}
