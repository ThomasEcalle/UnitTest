package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.UserInscription;
import grouptest.exceptions.AlreadyExistingEmail;
import grouptest.exceptions.AlreadyExistingPseudo;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by half-shell on 11/06/17.
 */
public final class UserInscriptionTest extends DatabaseTest
{
    UserInscription inscription = new UserInscription();

    @Test
    public void should_insert_a_new_user() throws SQLException
    {

        inscription.insertUser("brick", "iliketrains", "awesomedude@trains.com");

        final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT pseudo, password, email FROM user WHERE pseudo = ?");
        statement.setString(1, "brick");

        final ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
        {
            assertThat(resultSet.getString(1)).isEqualTo("brick");
            assertThat(resultSet.getString(2)).isEqualTo("iliketrains");
            assertThat(resultSet.getString(3)).isEqualTo("awesomedude@trains.com");
        }
    }

    @Test(expected = AlreadyExistingPseudo.class)
    public void should_return_invalid_entry_user_exists() throws SQLException
    {
        inscription.insertUser("half-shell", "okok.", "michelforever@trains.com");
        inscription.insertUser("half-shell", "iliketrains", "awesomedude@trains.com");
    }

    @Test(expected = AlreadyExistingEmail.class)
    public void should_return_invalid_entry_email_exists() throws SQLException
    {
        inscription.insertUser("tomtom", "metpocebleu", "awesomedude@trains.com");
        inscription.insertUser("brick", "iliketrains", "awesomedude@trains.com");
    }

}
