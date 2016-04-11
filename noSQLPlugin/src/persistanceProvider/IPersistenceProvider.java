package persistanceProvider;

import DAOs.IGameDAO;
import DAOs.IUserDAO;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public interface IPersistenceProvider {
    /**
     * Please Override
     * This function must generate a Game DAO in all plugins
     * @pre none
     * @post throws an error.
     * @throws Exception
     */
    public IGameDAO generateGameDAO() throws Exception;

    /**
     * Please Override
     * This function must generate a User DAO in all plugins
     * @pre none
     * @post throws an error.
     * @throws Exception
     */
    public IUserDAO generateIUserDAO() throws Exception;
}
