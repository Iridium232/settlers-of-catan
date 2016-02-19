package client.map;

import java.util.*;

import shared.communication.fromServer.games.Player;
import shared.definitions.*;
import shared.locations.*;
import shared.model.Fascade;
import shared.model.map.Edge;
import shared.model.map.Vertex;
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
	private Reference client_info;
	private IServerProxy proxy;

	/**
	 * Map Controller Constructor
	 * @param view
	 * @param robView
	 * @post creates a map controller tied to the GUI views
	 */
	public MapController(IMapView view, IRobView robView, 
			Fascade facade, Reference reference) 
	{
		super(view);
		
		setRobView(robView);
		client_info = reference;
		model = facade;
		initFromModel();
		
		updateMap();
		proxy = client_info.proxy;
	}
	
	/**
	 * Builds the map view from information in the model
	 * 
	 * @pre none
	 * @post the model's information is displayed in the map view
	 * 
	 */
	protected void initFromModel() {
		
		//<temp>
		
		Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
						CatanColor.BLUE);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
						CatanColor.ORANGE);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
							CatanColor.BLUE);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
							CatanColor.ORANGE);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
				}
			}
		}
		
		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);
		
		getView().placeRobber(new HexLocation(0, 0));
		
		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);
		
		//</temp>
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
	public boolean canPlaceRoad(EdgeLocation edgeLoc) 
	{
		Edge edge = new Edge();
		return model.canBuildRoad(client_info.player_index, edge);
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
	public boolean canPlaceSettlement(VertexLocation vertLoc) 
	{
		Vertex vertex = new Vertex(vertLoc);
		return model.canBuildSettlement(client_info.player_index, vertex);
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
	public boolean canPlaceCity(VertexLocation vertLoc) 
	{
		Vertex vertex = new Vertex(vertLoc);
		return model.canBuildCity(client_info.player_index, vertex);
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
	public boolean canPlaceRobber(HexLocation hexLoc) 
	{
		return model.canPlaceRobber(hexLoc, client_info.player_index);
	}

	/**
	 * The Player chose to place a road at this edge
	 * 
	 * @pre the model says they can put a road there
	 * @post the map shows a road on that edge
	 * 
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, client_info.player_color);
	}

	/**
	 * The Player chose to place a settlement at this vertex
	 * 
	 * @pre the model says they can put a settlement there
	 * @post the map shows a settlement on that vertex
	 * 
	 */
	public void placeSettlement(VertexLocation vertLoc) 
	{
		getView().placeSettlement(vertLoc, client_info.player_color);
	}

	/**
	 * The Player chose to place a city at this vertex
	 * 
	 * @pre the model says they can put a city there
	 * @post the map shows a city on that vertex
	 * 
	 */
	public void placeCity(VertexLocation vertLoc) 
	{
		getView().placeCity(vertLoc, client_info.player_color);
	}

	/**
	 * The Player chose where to put the robber
	 * 
	 * @pre the player rolled a seven and so gets to move the robber
	 * @pre the player clicked on this hexlocation
	 * @post the robber is on the place that the user clicked
	 * 
	 */
	public void placeRobber(HexLocation hexLoc) 
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
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
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
		
	}
	
	/**
	 * The Soldier Card was played. The map should not do anything.
	 * 
	 * @pre the soldier card was played
	 * @post nothing
	 * 
	 */
	public void playSoldierCard() 
	{	
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
		Player player = new Player("2","2", client_info.player_index);
		
		
		//proxy.robPlayer(  , 0);
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
	 */
	private void updateMap() 
	{
		
		
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
	public Fascade getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Fascade model) {
		this.model = model;
	}

	public void register() 
	{
		model.addObserver(this);
	}


}

