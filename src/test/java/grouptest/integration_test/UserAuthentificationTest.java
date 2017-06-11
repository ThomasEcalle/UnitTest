package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.User;
import grouptest.UserAuthentificationService;
import grouptest.UserDatabaseService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by spyro on 07/06/2017.
 */
public final class UserAuthentificationTest extends DatabaseTest
{

    private static Connection connection;

    protected User mainUser = new User.Builder()
            .pseudo("sanchyu")
            .email("thomas@hotmail.fr")
            .password("toto")
            .build();

    protected UserDatabaseService userDatabaseService = new UserDatabaseService();

    @Before
    public void insertion_before() throws SQLException
    {
        userDatabaseService.insertUser(mainUser);
    }

    @Test
    public void should_return_null_combinaison_inexistant() throws SQLException
    {
        UserAuthentificationService auth = new UserAuthentificationService();

        User user = auth.connectUser("jean", "pougetoux");
        Assert.assertNull(user);

    }

    @Test
    public void should_return_valid_user_combinaison_existant() throws SQLException
    {
        UserAuthentificationService auth = new UserAuthentificationService();
        User user = auth.connectUser("sanchyu", "toto");
        Assert.assertNotNull(user);
        Assert.assertEquals("sanchyu", user.getPseudo());
        Assert.assertEquals("toto", user.getPassword());
        Assert.assertEquals("thomas@hotmail.fr", user.getEmail());
    }
}
