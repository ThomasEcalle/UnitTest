package grouptest;

import java.sql.SQLException;

/**
 * Created by Paul on 08/06/2017.
 */
public interface IUserInscription {
    // return an int depending of the failures or the success (success = 0)
    void insertUser(String pseudo, String password, String email) throws SQLException;
}

