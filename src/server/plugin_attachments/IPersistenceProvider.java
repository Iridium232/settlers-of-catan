package server.plugin_attachments;

/**
 * Abstract parent of Data Access Plugin Factories
 * @author jeyrey
 *
 */
public interface IPersistenceProvider 
{
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
