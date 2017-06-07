package grouptest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Robin on 07/06/2017.
 */
public class UserAuthentificationService implements IAuthentificationService{
    private Connection connection;

    public UserAuthentificationService()
    {
        this.connection = DatabaseManager.getConnection();
    }

    public User connectUser(String pseudo, String pwd) throws SQLException
    {
        User user = null;
        final PreparedStatement statement = connection.prepareStatement("Select * from user where pseudo = ? and password = ?");
        statement.setString(1, pseudo);
        statement.setString(2, pwd);




        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
        {
            user = new User.Builder()
                        .pseudo(resultSet.getString("pseudo"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
        }
        return user;
    }

    public static void main(String[] args) throws SQLException
    {
        UserAuthentificationService userAuthentificationService = new UserAuthentificationService();
        User user = userAuthentificationService.connectUser("toto", "tataa");
        System.out.println( user);
    }
}
