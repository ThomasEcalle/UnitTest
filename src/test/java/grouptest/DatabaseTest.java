package grouptest;

import grouptest.exceptions.NotExistingEmail;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest
{
    private static Connection connection;

    private User mainUser = new User.Builder()
            .pseudo("sanchyu")
            .email("thomas@hotmail.fr")
            .password("toto")
            .build();

    static UserDatabaseService userDatabaseService = new UserDatabaseService();

    @BeforeClass
    public static void initConnection() throws SQLException
    {
        connection = DatabaseManager.getConnection();
    }

    @Before
    public void fillDatabase() throws SQLException
    {
        DatabaseManager.clearTable("user");

        userDatabaseService.insertUser(mainUser);
    }


    @Test
    public void should_reset_user_password_by_email() throws SQLException
    {
        final String newPass = userDatabaseService.resetPasswordByEmail(mainUser.getEmail());

        final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT password FROM user WHERE id = ?");
        statement.setLong(1, mainUser.getId());

        final ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        Assertions.assertThat(resultSet.getString(1)).isEqualTo(newPass);
    }

    @Test(expected = NotExistingEmail.class)
    public void should_throw_notExistingEmailException_when_email_is_not_in_database() throws SQLException
    {
        final String newPass = userDatabaseService.resetPasswordByEmail("totogro.fr");
    }


    @After
    public void clearDatabase() throws SQLException
    {
        //  DatabaseManager.clearTable("user");
    }

    @AfterClass
    public static void closeConnection()
    {
        try
        {
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
