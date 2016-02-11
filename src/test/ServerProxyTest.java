package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import client.communication.ClientCommunicator;
import client.communication.ModelPopulator;
import client.communication.ServerProxy;
import shared.communication.ResourceList;
import shared.communication.fromServer.games.Game;
import shared.communication.toServer.game.Commands;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.communication.fromServer.game.VertexLocation;
import shared.model.Fascade;
import shared.model.Player;

public class ServerProxyTest {

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

	@Test
	public void testLogin() {
		String result=sp.login("Pete", "pete");
		assertEquals (result,"200");
	}
	
	@Test
	public void testRegister(){
		Random rand = new Random(System.currentTimeMillis());
		String result=sp.register(Integer.toString(rand.nextInt(12)), "greg");
		assertEquals(result,"200");
	}
	
	@Test
	public void testGetGameList() 
	{
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
		} catch (Exception e) {
			assertTrue(false);
		}
		List<Game> result=sp.getGameList();
		assertFalse(result==null);
	}
	
	@Test
	public void testCreateGame() {
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Game test=sp.createGame("testGame", false, false, false);
		assert(test.getPlayers()[0].getName()==null);
		assert(test.getTitle().equals("testGame"));
		assert(test.getId()!=0);		
	}
	
	@Test
	public void testJoinGame() {
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		String test=sp.joinGame("Bob", 3, CatanColor.RED);
		assertFalse(test.equals("FAILED\n"));
	}

	@Test
    public void testGetModel() {
        String result = "";
        try {
            sp.login("Pete", "pete");
            sp.joinGame("pete", 0, CatanColor.RED);
            //ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            result = sp.getModel(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertFalse(result.equals("FAILED\n"));
	}

	@Test
	public void testSendChat() {
		String test = null;
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
			test = sp.sendChat(2, "I am testing sendChat");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(test.equals("FAILED\n"));
		assertNotNull(test);
		assertTrue(test.length() > 0);
	}

	@Test
	public void testAcceptTrade() {
		FileReader fr;
        StringBuilder sb=new StringBuilder();
        String line;
        try {
            fr = new FileReader("commands.txt");
            BufferedReader br=new BufferedReader(fr);
            while((line=br.readLine())!=null){
                sb.append(line);
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = "";
        try {
            sp.login("Pete", "pete");
            sp.joinGame("pete", 0, CatanColor.RED);
            //ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            sp.finishTurn();
            result = sp.acceptTrade(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertFalse(result.equals("FAILED\n"));
	}

	@Test
	 public void testRollNumber() {
        FileReader fr;
        StringBuilder sb=new StringBuilder();
        String line;
        try {
            fr = new FileReader("commands.txt");
            BufferedReader br=new BufferedReader(fr);
            while((line=br.readLine())!=null){
                sb.append(line);
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = "";
        try {
            sp.login("Pete", "pete");
            sp.joinGame("pete", 0, CatanColor.RED);
            //ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            
            result = sp.rollNumber(3);
            assertFalse(result.equals("FAILED\n"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assert(!result.isEmpty());
	}

	@Test
	public void testBuildRoad() {
		try {
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildRoad(true, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast));
			assertFalse(mon.equals("FAILED\n"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBuildSettlement() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildCity(new VertexLocation(VertexDirection.SouthWest,2,1));
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBuildCity() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildCity(new VertexLocation(VertexDirection.SouthWest, 0, 0));
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMaritimeTrade() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.maritimeTrade(2, ResourceType.BRICK, ResourceType.ORE);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	@Test
	public void testRobPlayer() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br = new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			Player v=new Player();
			v.setPlayerIndex(0);
			String mon=sp.robPlayer(new HexLocation(0,0), v);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	@Test
    public void testFinishTurn() {
        FileReader fr;
        StringBuilder sb=new StringBuilder();
        String line;
        try {
            fr = new FileReader("commands.txt");
            BufferedReader br=new BufferedReader(fr);
            while((line=br.readLine())!=null){
                sb.append(line);
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = "";
        try {
            sp.login("Pete", "pete");
            sp.joinGame("pete", 0, CatanColor.RED);
            //ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            sp.finishTurn();
            result = sp.finishTurn();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertFalse(result.equals("FAILED\n"));
    }


	@Test
	public void testBuyDevCard() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buyDevCard();
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPlaySoldier() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			Player v=new Player();
			v.setPlayerIndex(2);
			String mon=sp.playSoldier(new HexLocation(-1,1), v);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testYearOfPlenty() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Mark", "mark");
			sp.joinGame("Pete", 0, CatanColor.BLUE);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.yearOfPlenty(ResourceType.WHEAT, ResourceType.ORE);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRoadBuilding() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			EdgeLocation one=new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast);
			EdgeLocation two=new EdgeLocation(new HexLocation(2,1),EdgeDirection.South);
			String mon=sp.RoadBuilding(one, two);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMonopoly() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Mark", "mark");
			sp.joinGame("Pete", 0, CatanColor.BLUE);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monopoly(ResourceType.BRICK);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMonument() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monument();
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDiscardCards() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			ResourceList rl=new ResourceList(1, 0, 0, 0, 0);
			String mon=sp.discardCards(rl);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testOfferTrade() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			//ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			//ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			ResourceList rl=new ResourceList(0,1,0,-1,0);
			Player p=new Player();
			p.setPlayerIndex(1);
			String mon=sp.offerTrade(rl, p);
			assertFalse(mon.equals("FAILED\n"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
