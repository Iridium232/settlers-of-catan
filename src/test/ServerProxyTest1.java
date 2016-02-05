package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.communication.ClientCommunicator;
import client.communication.ModelPopulator;
import client.communication.ServerProxy;
import shared.model.Fascade;

public class ServerProxyTest1 {

	private static ServerProxy sp;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Fascade f=new Fascade();
		JSONObject json;
		sp=new ServerProxy("localhost",8081,f);
		ClientCommunicator.getSingleton("localhost", "8081");
		try 
		{
			 json = new JSONObject(SetupModel.model1);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
			return;
		}
		ModelPopulator.populateModel(json,f);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		String result=sp.login("bob", "bob");
		System.out.println(result);
		assertTrue (result=="Success");
	}

}
