package client.map;

import java.util.*;

import shared.communication.fromServer.game.Port;
import shared.communication.fromServer.games.Player;
import shared.definitions.*;
import shared.locations.*;
import shared.model.Fascade;
import shared.model.map.Edge;
import shared.model.map.Road;
import shared.model.map.Robber;
import shared.model.map.TerrainHex;
import shared.model.map.Vertex;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;
import shared.model.states.IState;
import client.base.*;
import client.communication.IServerProxy;
import client.control.IObserver;
import client.control.Reference;
import client.data.*;


/**
 * Implementation for the map controller.
 * Holds the Rob view.
 * Also has a pointer to the facade of the model.
 */
public class MapController extends Controller implements IMapController, IObserver {
	
	private IRobView robView;
	private Fascade model;
	private Reference reference;
	private IServerProxy proxy;
	private IState model_state;
	private boolean is_free = false;
	private boolean initialized = false;

	/**
	 * Map Controller Constructor
	 * @param view
	 * @param robView
	 * @post creates a map controller tied to the GUI views
	 */
	public MapController(IMapView view, IRobView robView) 
	{
		super(view);
		
		setRobView(robView);
		reference = Reference.GET_SINGLETON();
		model = reference.getFascade();
		//initFromModel();
		proxy = reference.proxy;
	}
	
	/**
	 * Builds the map view from information in the model
	 * 
	 * @pre none
	 * @post the model's information is displayed in the map view
	 * 
	 */
	protected void initFromModel() 
	{
		model = reference.fascade;
		if(!initialized)
		{		
			if(model == null){return;}
			TerrainHex[][] hex_grid = model.getHexes();
			//getView().clear();

		
			if(hex_grid != null)
			{
				for(TerrainHex[] hex_list : hex_grid)
				{
					for(TerrainHex hex : hex_list)
					{
						if(hex == null)continue;
						if(hex.getType() == null) continue;
						if(hex.getLocation() == null)continue;
						getView().addHex(hex.getLocation(), hex.getType());
						if(hex.getNumber() == null || hex.getNumber().getValue() == 0)
						{
							continue;
						}
						getView().addNumber(hex.getLocation(), hex.getNumber().getValue());
					}
				}
			}
		
		initialized = true;
		}
		
		
		
		Road[] road_list = model.getRoads();
		if(road_list != null)
		{
			for (Road road : road_list)
			{
				getView().placeRoad(road.getLocation().getLocation(), road.getColor());
			}
		}
		
		shared.model.ports.Port[] port_list = model.getPorts();
		if(port_list != null)
		{
			for (shared.model.ports.Port port : port_list)
			{
				PortType type = getPortType(port.getResource());
				getView().addPort(new EdgeLocation(port.getLocation(),port.getEdgeDirection()), type);
			}
		}
		
		City[] city_list = model.getCities();
		if(city_list != null)
		{
			for (City city : city_list)
			{
				getView().placeCity(city.getLocation().getNormalizedLocation(), city.getColor());
			}
		}
		
		Settlement[] settlement_list = model.getSettlements();
		if (settlement_list != null)
		{
			for (Settlement settlement: settlement_list)
			{
				getView().placeSettlement(settlement.getLocation().getNormalizedLocation(),
						settlement.getColor());
			}
		}
		
		Robber robber = model.getRobber();
		if(robber!= null)
		{
			getView().placeRobber(robber.getLocation());
		}
		this.model_state = model.getStateOf(reference.player_index);
		if(model_state.getState() == TurnStatus.ROBBING)
		{
			this.startMove(PieceType.ROBBER, false, false);
		}
		
		if(model_state.getState() == TurnStatus.FIRSTROUND)
		{
			this.startMove(PieceType.ROAD, true, true);
			this.startMove(PieceType.SETTLEMENT, true, false);
		}
		
		if(model_state.getState() == TurnStatus.SECONDROUND)
		{
			this.startMove(PieceType.ROAD, true, true);
			this.startMove(PieceType.SETTLEMENT, true, false);
		}
		
	}

	private PortType getPortType(ResourceType resource) 
	{
		if (resource == ResourceType.BRICK) return PortType.BRICK;
		if (resource == ResourceType.ORE) return PortType.ORE;
		if (resource == ResourceType.SHEEP) return PortType.SHEEP;
		if (resource == ResourceType.WHEAT) return PortType.WHEAT;
		if (resource == ResourceType.WOOD) return PortType.WOOD;
		return PortType.THREE;
	}

	/**
	 * Checks with the model to see if the player can place a road
	 * at the specified edge.
	 * 
	 * @param edgeLoc
	 * 
	 * @pre none
	 * @post result = true iff the model says this is valid
	 * 
	 */
	public boolean canPlaceRoad(shared.locations.EdgeLocation edgeLoc) 
	{
		Edge edge = new Edge();
		return model.canBuildRoad(reference.player_index, edge);
	}

	/**
	 * Checks with the model to see if the player can place a settlement
	 * at the specified spot.
	 * 
	 * @param vertLoc
	 * 
	 * @pre none
	 * @post result = true iff the model says this is valid
	 * 
	 */
	public boolean canPlaceSettlement(shared.locations.VertexLocation vertLoc) 
	{
		Vertex vertex = new Vertex(vertLoc);
		return model.canBuildSettlement(reference.player_index, vertex);
	}

	/**
	 * Checks with the model to see if the player can place a city
	 * at the specified spot.
	 * 
	 * @param vertLoc
	 * 
	 * @pre none
	 * @post result = true iff the model says this is valid
	 * 
	 */
	public boolean canPlaceCity(shared.locations.VertexLocation vertLoc) 
	{
		Vertex vertex = new Vertex(vertLoc);
		return model.canBuildCity(reference.player_index, vertex);
	}

	/**
	 * Checks with the model to see if the player can place the robber
	 * at the specified spot.
	 * 
	 * @param hexLoc
	 * 
	 * @pre none
	 * @post result = facade.canPlaceRobber(hexLoc)
	 * 
	 */
	public boolean canPlaceRobber(shared.locations.HexLocation hexLoc) 
	{
		return model.canPlaceRobber(hexLoc, reference.player_index);
	}

	/**
	 * The Player chose to place a road at this edge
	 * 
	 * @pre the model says they can put a road there
	 * @post the map shows a road on that edge
	 * 
	 */
	public void placeRoad(shared.locations.EdgeLocation edgeLoc) 
	{
		if(model_state.getState() == shared.definitions.TurnStatus.FIRSTROUND
				|| model_state.getState() == shared.definitions.TurnStatus.SECONDROUND)
		{
			is_free = true;
		}
		shared.communication.EdgeLocation sending_edge = 
				new shared.communication.EdgeLocation(edgeLoc.getHexLoc()
						.getX(),edgeLoc.getHexLoc().getY(), edgeLoc.getDir());
		getView().placeRoad(edgeLoc, reference.player_color);
		proxy.buildRoad(is_free ,sending_edge);
		is_free = false;
	}

	/**
	 * The Player chose to place a settlement at this vertex
	 * 
	 * @pre the model says they can put a settlement there
	 * @post the map shows a settlement on that vertex
	 * 
	 */
	public void placeSettlement(shared.locations.VertexLocation vertLoc) 
	{
		shared.communication.fromServer.game.VertexLocation sending_location = 
				new shared.communication.fromServer.game.VertexLocation(vertLoc.getDir(),
						vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY());
		boolean free = model_state.getState() == TurnStatus.FIRSTROUND;
		free = free && model_state.getState() == TurnStatus.SECONDROUND;
		reference.proxy.buildSettlement(free, sending_location);
		getView().placeSettlement(vertLoc, reference.player_color);
	}

	/**
	 * The Player chose to place a city at this vertex
	 * 
	 * @pre the model says they can put a city there
	 * @post the map shows a city on that vertex
	 * 
	 */
	public void placeCity(shared.locations.VertexLocation vertLoc) 
	{
		shared.communication.fromServer.game.VertexLocation sending_location = 
				new shared.communication.fromServer.game.VertexLocation(vertLoc.getDir(),
						vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY());
		reference.proxy.buildCity(sending_location);
		getView().placeCity(vertLoc, reference.player_color);
	}

	/**
	 * The Player chose where to put the robber
	 * 
	 * @pre the player rolled a seven and so gets to move the robber
	 * @pre the player clicked on this hexlocation
	 * @post the robber is on the place that the user clicked
	 * 
	 */
	public void placeRobber(shared.locations.HexLocation hexLoc) 
	{
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	/**
	 * The Player is placing a piece
	 * 
	 * @pre the player has decided to play a piece
	 * @post the map shows options of placement for that piece.
	 * 
	 */
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) 
	{	
		if(getView().isDropping()) return;
		getView().startDrop(pieceType, reference.player_color, !isFree);
	}
	
	/**
	 * The Player is no longer placing a piece
	 * 
	 * @pre the player was placing a piece
	 * @post the map no longer shows options of placement for that piece.
	 * 
	 */
	public void cancelMove() 
	{
		//Do nothing
	}
	
	/**
	 * The Soldier Card was played. The map should allow a robbing.
	 * 
	 * @pre the soldier card was played
	 * @post the 
	 * 
	 */
	public void playSoldierCard() 
	{	
		this.startMove(PieceType.ROBBER, false, false);
		return;
	}
	
	/**
	 * The Road-Building Card is played so the player needs to choose 2 places to build a road
	 * 
	 * @pre 
	 * @post 
	 * 
	 */
	public void playRoadBuildingCard() 
	{	
		is_free = true;
		this.startMove(PieceType.ROAD, true, false);
		
		this.startMove(PieceType.ROAD, true, false);
		is_free = false;
		//Send off the info to the controller
	}
	
	/**
	 * The active player robs the other player
	 * 
	 * @pre the player rolled a seven and moved the robber and chose to rob this
	 * player
	 * @post all are informed that this player was robbed and the robbing player
	 * gets the resource
	 */
	public void robPlayer(RobPlayerInfo victim) 
	{
		shared.locations.HexLocation location = getView().getMap().getRobber();
		
		proxy.robPlayer(location, new shared.model.player.Player(victim));
		getRobView().closeModal();
	}
	
	/**
	 * This method is called by the facade when there has been a change made.
	 * 
	 * @pre the model changed
	 * @post the GUI represents the data in the model.
	 */
	@Override
	public void ObservableChanged() 
	{
		updateMap();
	}
	
	/**
	 * Asks the model for all of the information to draw the current map.
	 * 
	 * @pre none
	 * @post the view represents the model's info
	 */
	private void updateMap() 
	{
		this.initFromModel();
	}

	

	/**
	 * Registers this observer with the observable model facade
	 * 
	 * @pre this controller was recently initialized
	 * @post this controller is registered with the model observable
	 */
	public void register() 
	{
		model.addObserver(this);
	}
	//*****************************GETTERS AND SETTERS***************************************
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	/**
	 * @return the model
	 */
	public Fascade getModel() 
	{
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Fascade model) 
	{
		this.model = model;
	}



}

