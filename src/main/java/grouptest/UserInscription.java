package grouptest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Paul on 08/06/2017.
 */
public class UserInscription implements IUserInscription{
    private Connection connection;

    public UserInscription()
    {
        this.connection = DatabaseManager.getConnection();
    }

    public int insertUser(String pseudo, String password, String email) throws SQLException{
        User userToInsert = new User.Builder()
                .pseudo(pseudo)
                .email(email)
                .password(password)
                .build();

        /*Gestion des Erreurs : return 1 = pseudo existant, reuturn 2 = email existant*/

        /*Pour le pseudo*/
        final PreparedStatement statementPseudo = connection.prepareStatement("Select * from user where pseudo = ?");
        statementPseudo.setString(1, pseudo);

        ResultSet resultSetPseudo = statementPseudo.executeQuery();

        if(resultSetPseudo != null){
            return 1;
        }

        /*Pour le mail*/
        final PreparedStatement statementEmail = connection.prepareStatement("Select * from user where email = ?");
        statementEmail.setString(1, email);

        ResultSet resultSetEmail = statementEmail.executeQuery();

        if(resultSetEmail != null){
            return 2;
        }

        /*Insertion*/
        final PreparedStatement statementInsert = connection.prepareStatement("INSERT INTO TABLE user VALUES (pseudo = ?, password = ?, email = ?");
        statementPseudo.setString(1, pseudo);
        statementPseudo.setString(2, password);
        statementPseudo.setString(3, email);

        return 0;
    }
}
