package server.plugin_attachments;

import java.util.Map;

import server.facade.User;

public interface IUserDAO 
{
	/**
	 * Interacts with a persistant database to get a list of stored users
	 * mapped to their user_id
	 * @pre none
	 * @post A map of all stored users is returned
	 * @return
	 */
	public Map<Integer, Object> getUsers();
	
	/**
	 * Add a User to the Database
	 * 
	 * @pre none
	 * @post the user is stored according to their user_id
	 * @param user
	 * @param user_id
	 */
	public void addUser(Object user, int user_id);
	
	/**
	 * Clear the list of users in the database
	 * 
	 * @pre none
	 * @post there are no stored users left
	 */
	public void clearAll();
}
