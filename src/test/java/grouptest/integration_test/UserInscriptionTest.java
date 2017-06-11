package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.User;
import grouptest.UserDatabaseService;
import grouptest.UserInscription;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by half-shell on 11/06/17.
 */
public class UserInscriptionTest {
    UserInscription inscription = new UserInscription();

   @Test
    public void should_insert_a_new_user() {
       try {
           inscription.insertUser("brick", "iliketrains", "awesomedude@trains.com");

           final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT pseudo, email, password FROM user WHERE pseudo = ?");
           statement.setString(1, "brick");

           final ResultSet resultSet = statement.executeQuery();

           resultSet.next();

           assertThat(resultSet.getString(1)).isEqualTo("brick");
           assertThat(resultSet.getString(2)).isEqualTo("iliketrains");
           assertThat(resultSet.getString(3)).isEqualTo("awesomedude@trains.com");
       } catch (SQLException e) {
           Assert.fail();
       }
   }

    @Test
    public void should_return_invalid_entry_user_exists(){
        try {
            inscription.insertUser("brick", "iliketrains", "awesomedude@trains.com");
            assertThat(inscription.insertUser("brick", "iliketrains", "dibdalou@blih.com")).isEqualTo(1);
        } catch (SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void should_return_invalid_entry_email_exists(){
        try {
            inscription.insertUser("half-shell", "iliketrains", "awesomedude@trains.com");
            assertThat(inscription.insertUser("brick", "iliketrains", "awesomedude@trains.com")).isEqualTo(2);
        } catch (SQLException e) {
            Assert.fail();
        }
    }

    @After
    public void clean_after()throws SQLException {
        DatabaseManager.clearTable("user");
    }
}
