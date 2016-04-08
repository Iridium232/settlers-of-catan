package DAOs;

import java.util.Map;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class UserDAO implements IUserDAO {

    /**
     * gets a list of stored users from a file
     * mapped to their user_id
     * @pre none
     * @post A map of all stored users is returned
     * @return
     */
    @Override
    public Map<Integer, Object> getUsers() {
        return null;
    }

    /**
     * Add the serialization of a User to a file
     *
     * @pre none
     * @post the user is stored according to their user_id
     * @param user
     * @param user_id
     */
    @Override
    public void addUser(Object user, int user_id) {

    }

    /**
     * Clear the list of users in the file
     *
     * @pre none
     * @post there are no stored users left
     */
    @Override
    public void clearAll() {

    }
}
