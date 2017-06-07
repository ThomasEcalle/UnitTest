package grouptest;

import java.sql.SQLException;

/**
 * Created by Robin on 07/06/2017.
 */
public interface IAuthentificationService {
    // return a user object if pseudo and password match or null
    User connectUser(String pseudo, String pwd) throws SQLException;

}
