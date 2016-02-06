package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
import shared.locations.VertexLocation;
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
		String result=sp.register("Greg", "greg");
		assertEquals(result,"200");
	}
	
	@Test
	public void testGetGameList() {
		List<Game> result=sp.getGameList();
		assert(!result.isEmpty());
	}
	
	@Test
	public void testCreateGame() {
		Game test=sp.createGame("testGame", false, false, false);
		assert(test.getPlayers().length==0);
		assert(test.getTitle()=="testGame");
		assert(test.getId()!=0);		
	}
	
	@Test
	public void testJoinGame() {
		String test=sp.joinGame("Bob", 3, CatanColor.RED);
		assert(!test.isEmpty());
	}

	@Test
    public void testGetModel() {
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
            ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            result = sp.getModel(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert(!result.isEmpty());
	}

	@Test
	public void testSendChat() {
		String test = null;
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
			test = sp.sendChat(10, "I am testing sendChat");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
            ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            sp.finishTurn();
            result = sp.acceptTrade(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assert(!result.isEmpty());
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
            ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            sp.finishTurn();
            result = sp.rollNumber(3);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assert(!result.isEmpty());
	}

	@Test
	public void testBuildRoad() {
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildRoad(true, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast));
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildCity(new VertexLocation(new HexLocation(2,1),VertexDirection.SouthWest));
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buildCity(new VertexLocation(new HexLocation(0,0),VertexDirection.SouthWest));
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.maritimeTrade(2, ResourceType.BRICK, ResourceType.ORE);
			assert(!mon.isEmpty());
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
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			Player v=new Player();
			v.setPlayerIndex(0);
			String mon=sp.robPlayer(new HexLocation(0,0), v);
			assert(!mon.isEmpty());
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
            ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
            sp.finishTurn();
            result = sp.finishTurn();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assert(!result.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.buyDevCard();
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			Player v=new Player();
			v.setPlayerIndex(2);
			String mon=sp.playSoldier(new HexLocation(-1,1), v);
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.yearOfPlenty(ResourceType.WHEAT, ResourceType.ORE);
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			EdgeLocation one=new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast);
			EdgeLocation two=new EdgeLocation(new HexLocation(2,1),EdgeDirection.South);
			String mon=sp.RoadBuilding(one, two);
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monopoly(ResourceType.BRICK);
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monument();
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			ResourceList rl=new ResourceList(1, 0, 0, 0, 0);
			String mon=sp.discardCards(rl);
			assert(!mon.isEmpty());
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
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			ResourceList rl=new ResourceList(0,1,0,-1,0);
			Player p=new Player();
			p.setPlayerIndex(1);
			String mon=sp.offerTrade(rl, p);
			assert(!mon.isEmpty());
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
