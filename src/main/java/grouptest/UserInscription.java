package grouptest;

import grouptest.exceptions.AlreadyExistingEmail;
import grouptest.exceptions.AlreadyExistingPseudo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Paul on 08/06/2017.
 */
public class UserInscription implements IUserInscription
{
    private Connection connection;

    public UserInscription()
    {
        this.connection = DatabaseManager.getConnection();
    }

    public void insertUser(String pseudo, String password, String email) throws SQLException
    {

        /*Pour le pseudo*/
        final PreparedStatement statementPseudo = connection.prepareStatement("Select * from user where pseudo = ?");
        statementPseudo.setString(1, pseudo);

        ResultSet resultSetPseudo = statementPseudo.executeQuery();

        if (resultSetPseudo.next())
        {
            throw new AlreadyExistingPseudo();
        }

        /*Pour le mail*/
        final PreparedStatement statementEmail = connection.prepareStatement("Select * from user where email = ?");
        statementEmail.setString(1, email);

        ResultSet resultSetEmail = statementEmail.executeQuery();

        if (resultSetEmail.next())
        {
            throw new AlreadyExistingEmail();
        }


        /*Insertion*/
        final PreparedStatement statementInsert = connection.prepareStatement("INSERT INTO user (pseudo, password, email) VALUES (?, ?, ?)");
        statementInsert.setString(1, pseudo);
        statementInsert.setString(2, password);
        statementInsert.setString(3, email);

        statementInsert.execute();
    }
}
