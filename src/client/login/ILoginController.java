package client.login;

import client.base.*;

import java.rmi.ServerException;

/**
 * Interface for the login controller
 */
public interface ILoginController extends IController
{
	
	/**
	 * Displays the login view
	 */
	void start();
	
	/**
	 * Called when the user clicks the "Sign in" button in the login view
	 */
	void signIn() throws ServerException;
	
	/**
	 * Called when the user clicks the "Register" button in the login view
	 */
	void register() throws ServerException;
	
}

