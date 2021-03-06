package server.facade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * Class in charge of keeping track of users on the server
 *
 */
public class User 
{
	private String name;
	private String password;
	private int playerID;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String password, int playerID) {
		this.name=name;
		this.password=password;
		this.playerID=playerID;
	}
	
	
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
//		java.security.MessageDigest crypto;
//		try 
//		{
//			crypto = MessageDigest.getInstance("MD5");
//		} 
//		catch (NoSuchAlgorithmException e) 
//		{
//			System.out.print("ERROR Setting user Password!");
//			e.printStackTrace();
//			return;
//		}
//		crypto.update(password.getBytes());
//		this.passwordHash = new String(crypto.digest());
		return;
	}
	
	public boolean checkPassword(String password)
	{
		if(this.password == null)
		{
			return false;
		}
		return this.password.equals(password);
//		java.security.MessageDigest crypto;
//		try 
//		{
//			crypto = MessageDigest.getInstance("MD5");
//		} 
//		catch (NoSuchAlgorithmException e) 
//		{
//			System.out.print("ERROR Checking user Password!");
//			e.printStackTrace();
//			return false;
//		}
//		crypto.update(password.getBytes());
//		return passwordHash.equals(new String(crypto.digest()));
	}
	
	
}
