package client.login;

import client.base.Controller;
import client.base.IAction;
import client.communication.ClientCommunicator;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import shared.communication.toServer.user.Credentials;
import shared.dataTransfer.cookie.CookieResponse;
import shared.dataTransfer.response.DataTransferResponse;
import shared.dataTransfer.response.LoginResponse;
import shared.definitions.Password;
import shared.definitions.PlayerName;
import shared.exceptions.InvalidNameException;
import shared.exceptions.InvalidPasswordException;
import shared.model.Fascade;

import java.rmi.ServerException;

/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private ClientCommunicator clientCommunicator;
	private Fascade clientFascade;
	private IMessageView messageView;
	private IAction loginAction;

	enum  Operation {LOGIN, REGISTER}
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) throws Exception {

		super(view);
		
		this.messageView = messageView;
		clientCommunicator = clientCommunicator.getSINGLETON();
		this.clientFascade = clientFascade;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	/**
	 * Displays the login view
	 */
	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	/**
	 * Called when the user clicks the "Sign in" button in the login view
	 * Should close the login view and show the main window
	 * Should communicate with the model and log in the user by calling login on the model facade
	 */
	@Override
	public void signIn() throws ServerException {

		this.doOperation(getLoginView().getLoginUsername(), getLoginView().getLoginPassword(), Operation.LOGIN);
	}

		// If log in succeeded


	/**
	 * Called when the user clicks the "Register" button in the login view
	 * Should close the login view and show the main window
	 * Should communicate with the model and register the user by calling register ont the model facade
	 */
	@Override
	public void register() throws ServerException {

		// TODO: register new user (which, if successful, also logs them in)
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passwordRepeat = getLoginView().getRegisterPasswordRepeat();

		if (!password.equals(passwordRepeat)) {
			messageView.setMessage("Warning!");
			messageView.setTitle("Passwords don't match.");
			messageView.showModal();
		} else {
			this.doOperation(username, password, Operation.REGISTER);
		}
	}

	private Credentials prepareCredentials(String username, String password) {
		Credentials credentials = null;
		try {
			credentials = new Credentials(new PlayerName(username), new Password(password));
		} catch (InvalidNameException | InvalidPasswordException e) {
		}
		return credentials;
	}

	private void doOperation(String givenUsername, String givenPassword, Operation operation) throws ServerException {
		Boolean success = false;
		String messageTitle = "";
		String message = "";
		String username = givenUsername;
		String password = givenPassword;
		Credentials credentials = null;
		DataTransferResponse response = null;
		PlayerInfo newLocalPlayer = null;

		credentials = prepareCredentials(username, password);
		if (credentials != null) {
			try {
				switch (operation) {
					case LOGIN:
						response = clientFascade.doLogin(credentials);
						break;
					case REGISTER:
						response = clientFascade.doRegister(credentials);
						break;
					default:
						// an error occurred with the server
						response = new LoginResponse("Failed to Login", true);
						break;
				}
				if (!response.isBad) {
					success = true;
				} else {
					messageTitle = "Error!";
					message = response.getResponseBody();
				}
			} catch (ServerException e) {
				e.printStackTrace();
				messageTitle = "Error!";
				message = "Internal Server Error";
			}
		} else {
			messageTitle = "Warning!";
			message = "Invalid Username or Password.";
		}

		if (success) {
			// If login succeeded
			clientFascade.setPlayerCookie(((CookieResponse) response).getCookie());
			getLoginView().closeModal();
			loginAction.execute();
		} else {
			messageView.setMessage(message);
			messageView.setTitle(messageTitle);
			messageView.showModal();
		}
	}

}

