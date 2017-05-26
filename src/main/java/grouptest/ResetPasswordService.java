package grouptest;

import java.sql.SQLException;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
public interface ResetPasswordService
{
    void resetPassword(final User user, final String password) throws SQLException;

    String resetPasswordByEmail(final String email) throws SQLException;
}
