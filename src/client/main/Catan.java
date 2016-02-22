package client.main;

import client.base.IAction;
import client.catan.CatanPanel;
import client.communication.ClientCommunicator;
import client.communication.IServerProxy;
import client.communication.ModelPopulator;
import client.communication.ServerPoller;
import client.communication.ServerProxy;
import client.control.Reference;
import client.join.*;
import client.login.LoginController;
import client.login.LoginView;
import client.misc.MessageView;
import shared.model.Fascade;

import javax.swing.*;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{
	
	private CatanPanel catanPanel;
	
	public Catan(String host, int port)
	{
		client.base.OverlayView.setWindow(this);
		
		this.setTitle("Settlers of Catan");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Reference reference = Reference.GET_SINGLETON();
		Fascade facade = new Fascade();
		IServerProxy proxy = new ServerProxy(host , port , facade);
		
		ServerPoller.getServerPoller();
		ServerPoller.setFascade(facade);
		ServerPoller.setServer(proxy);
		ServerPoller.setComm(ClientCommunicator.getSingleton(host,Integer.toString(port)));
		ServerPoller.Start();

		reference.setProxy(proxy);
		
		
		catanPanel = new CatanPanel();
		this.setContentPane(catanPanel);
		
		display();
	}
	
	private void display()
	{
		pack();
		setVisible(true);
	}
	
	//
	// Main
	//
	
	public static void main(final String[] args)
	{
		Reference.GET_SINGLETON();
		String host = "localhost";
		String port = "8088";
		if(args.length > 0)
		{
			host = args[0];
			System.out.print("Running client on host: http://" + host + ".\n");
		}
		else
		{
			System.out.print("\nNo host specified. Using default 'localhost'.");
		}
		if(args.length > 1)
		{
			port = args[1];
			System.out.print("Port: " + port +".\n");
		}
		else
		{
			System.out.print("\nNo port specified. Using default '8088'.");
		}
		Reference reference = Reference.GET_SINGLETON();
		reference.setPort(new Integer(port));
		reference.setHost("http://" + host);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				Reference reference = Reference.GET_SINGLETON();
				new Catan(new String(reference.getHost()),new Integer(reference.getPort()));
				
				PlayerWaitingView playerWaitingView = new PlayerWaitingView();
				final PlayerWaitingController playerWaitingController = new PlayerWaitingController(
																									playerWaitingView);
				playerWaitingView.setController(playerWaitingController);
				
				JoinGameView joinView = new JoinGameView();
				NewGameView newGameView = new NewGameView();
				SelectColorView selectColorView = new SelectColorView();
				MessageView joinMessageView = new MessageView();
				final JoinGameController joinController = new JoinGameController(
																				 joinView,
																				 newGameView,
																				 selectColorView,
																				 joinMessageView);
				joinController.setJoinAction(new IAction() {
					@Override
					public void execute()
					{
						playerWaitingController.start();
					}
				});
				joinView.setController(joinController);
				newGameView.setController(joinController);
				selectColorView.setController(joinController);
				joinMessageView.setController(joinController);
				
				LoginView loginView = new LoginView();
				MessageView loginMessageView = new MessageView();
				LoginController loginController = null;
				try {
					loginController = new LoginController(
							loginView,
							loginMessageView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				loginController.setLoginAction(new IAction() {
					@Override
					public void execute()
					{
						joinController.start();
					}
				});
				loginView.setController(loginController);
				loginView.setController(loginController);
				
				loginController.start();
			}
		});
	}
	
}

