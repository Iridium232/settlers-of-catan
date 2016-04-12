package plugin.persistanceProvider;

import plugin.DAOs.GameDAO;
import plugin.DAOs.UserDAO;
import server.plugin_attachments.IGameDAO;
import server.plugin_attachments.IPersistenceProvider;
import server.plugin_attachments.IUserDAO;


/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class NoSQLFactory implements IPersistenceProvider {
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
