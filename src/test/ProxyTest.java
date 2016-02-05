package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import client.communication.ClientCommunicator;
import client.communication.ModelPopulator;
import client.communication.ServerProxy;
import shared.model.Fascade;

public class ProxyTest {
	static ServerProxy sp;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Fascade f=new Fascade();
		JSONObject json=null;
		sp=new ServerProxy("localhost",8081,f);
		ClientCommunicator.getSingleton("localhost","8081");
		try{
			json=new JSONObject(SetupModel.model1);
		} catch(JSONException e){
			e.printStackTrace();
			System.out.println("FAILURE");
		}
		ModelPopulator.populateModel(json,f);
	}

	@Test
	public void loginTest(){
		String result =sp.login("bob", "bob");
		System.out.println(result);
		assertTrue(result=="Success");
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
