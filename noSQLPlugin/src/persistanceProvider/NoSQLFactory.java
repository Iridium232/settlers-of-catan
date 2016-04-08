package persistanceProvider;

import DAOs.GameDAO;
import DAOs.IGameDAO;
import DAOs.IUserDAO;
import DAOs.UserDAO;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class NoSQLFactory extends PersistanceProvider {
    /**
     * Generates a Game DAO for serializing games to a file
     * @pre none
     * @post A DAO for reading and writing games to a file is created
     * @throws Exception
     */
    @Override
    public IGameDAO generateGameDAO() throws Exception {
        return new GameDAO();
    }

    /**
     * Please Override
     * This function must generate a User DAO in all plugins
     * @pre none
     * @post A DAO for reading and writing users to a file is created
     * @throws Exception
     */
    @Override
    public IUserDAO generateIUserDAO() throws Exception {
        return new UserDAO();
    }
}
