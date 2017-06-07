package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.User;
import grouptest.UserAuthentificationService;
import grouptest.UserDatabaseService;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by spyro on 07/06/2017.
 */
public final class UserAuthentificationTest {

    private static Connection connection;
    protected User mainUser = new User.Builder()
            .pseudo("sanchyu")
            .email("thomas@hotmail.fr")
            .password("toto")
            .build();
    protected UserDatabaseService userDatabaseService = new UserDatabaseService();

    @Before
    public void insertion_before()throws SQLException{
        DatabaseManager.clearTable("user");
        userDatabaseService.insertUser(mainUser);
    }

    @Test
    public void should_return_null_combinaison_inexistant() {
        UserAuthentificationService auth = new UserAuthentificationService();
        try {
            User user = auth.connectUser("jean", "pougetoux");
            Assert.assertNull(user);
        } catch (SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void should_return_valid_user_combinaison_existant(){
        UserAuthentificationService auth = new UserAuthentificationService();
        try{
            User user = auth.connectUser("sanchyu", "toto");
            Assert.assertNotNull(user);
            Assert.assertEquals("sanchyu", user.getPseudo());
            Assert.assertEquals("toto", user.getPassword());
            Assert.assertEquals("thomas@hotmail.fr", user.getEmail());
        } catch (SQLException e) {

        }
    }

    @After
    public void clean_after()throws SQLException{
        DatabaseManager.clearTable("user");
    }
}
