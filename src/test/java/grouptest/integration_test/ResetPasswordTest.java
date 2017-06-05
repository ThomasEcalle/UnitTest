package grouptest.integration_test;

import grouptest.DatabaseManager;
import grouptest.exceptions.NotExistingEmail;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas Ecalle on 05/06/2017.
 */
public final class ResetPasswordTest extends DatabaseTest
{

    @Before
    public void insertion_before() throws SQLException
    {
        userDatabaseService.insertUser(mainUser);
    }

    @Test
    public void should_reset_user_password_by_email() throws SQLException
    {
        // Try to reset password and to get the new one
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
}
