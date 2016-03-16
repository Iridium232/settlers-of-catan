package server.handlers;

/**
 * Handler Interface for all handlers. These handlers should 
 * generate commands and send them to the server facade.
 *
 */
public interface IHandler 
{
	public Object handle();
}
