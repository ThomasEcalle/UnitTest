package grouptest;

import grouptest.exceptions.NotExistingEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
public final class UserDatabaseService implements ResetPasswordService
{
    private Connection connection;

    public UserDatabaseService()
    {
        this.connection = DatabaseManager.getConnection();
    }


    public void insertUser(final User user) throws SQLException
    {
        final PreparedStatement statement = connection.prepareStatement("INSERT INTO user (pseudo, password, email) VALUES (?, ?, ?)");
        statement.setString(1, user.getPseudo());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());

        statement.execute();

        final ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next())
        {
            user.setId(generatedKeys.getLong(1));
        }
    }


    @Override
    public String resetPasswordByEmail(final String email) throws SQLException
    {
        // Check for user
        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
        preparedStatement.setString(1, email);


        final ResultSet resultSet = preparedStatement.executeQuery();

        //Create User
        final User.Builder builder = new User.Builder();

        User user = null;


        if (resultSet.next())
        {
            builder.id(resultSet.getLong(1));
            builder.pseudo(resultSet.getString(2));
            builder.password(resultSet.getString(3));
            builder.email(resultSet.getString(4));

            user = builder.build();


            //Send email with new password
            final String wtf = "azertyuiopqsdfghjklmnbvcxw1234578963*$^ù**^^$*";
            StringBuilder stringBuilder = new StringBuilder();

            //Building a random new Password
            final Random random = new Random();

            for (int index = 0; index < 6; index++)
            {
                stringBuilder.append(wtf.charAt(random.nextInt(wtf.length())));
            }

            final String newpass = stringBuilder.toString();


            resetPassword(user, newpass);
            return newpass;
        } else
        {
            throw new NotExistingEmail();
        }
    }

    @Override
    public void resetPassword(User user, String password) throws SQLException
    {
        final PreparedStatement statement = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
        statement.setString(1, password);
        statement.setLong(2, user.getId());

        statement.execute();
    }
}
