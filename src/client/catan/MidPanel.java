package client.catan;

import java.awt.*;
import javax.swing.*;

import shared.model.Fascade;

import client.control.Reference;
import client.map.*;

@SuppressWarnings("serial")
public class MidPanel extends JPanel
{
	
	private TradePanel tradePanel;
	private MapView mapView;
	private RobView robView;
	private MapController mapController;
	private GameStatePanel gameStatePanel;
	private Reference reference;
	private Fascade facade;
	
	public MidPanel(Reference ref, Fascade facade)
	{
		this.facade = facade;
		this.reference = ref;
		this.setLayout(new BorderLayout());
		
		tradePanel = new TradePanel();
		
		mapView = new MapView();
		robView = new RobView();
		mapController = new MapController(mapView, robView, facade,reference);
		mapController.register();
		mapView.setController(mapController);
		robView.setController(mapController);
		
		gameStatePanel = new GameStatePanel();
		
		this.add(tradePanel, BorderLayout.NORTH);
		this.add(mapView, BorderLayout.CENTER);
		this.add(gameStatePanel, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(800, 700));
	}
	
	public GameStatePanel getGameStatePanel()
	{
		return gameStatePanel;
	}
	
	public IMapController getMapController()
	{
		return mapController;
	}
	
}

