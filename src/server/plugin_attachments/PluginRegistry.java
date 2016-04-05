package server.plugin_attachments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A plugin registry that allows plugins to be loaded
 * @author jeyrey
 *
 */
public class PluginRegistry 
{
	private ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
	private static PluginRegistry SINGLETON;
	
	/**
	 * Default Constructor for the Singelton
	 */
	private PluginRegistry()
	{
		
	}
	
	/**
	 * @pre the file exists and is formatted properly
	 * @post all plugins in that file are registered
	 * @param filename 
	 * @throws FileNotFoundException 
	 */
	public void LoadInPlugins(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(filename));
		scanner.useDelimiter("\\s");
		while(scanner.hasNext())
		{
			PluginInfo new_plugin = new PluginInfo();
			new_plugin.setPlugin_name(scanner.next());
			new_plugin.setMain_class_name(scanner.next());
			new_plugin.setJar_relative_uri(scanner.next());
			plugins.add(new_plugin);
		}
		scanner.close();
	}
	
	/**
	 * Gets the 
	 * @return the Singleton of this class
	 */
	public static PluginRegistry getSingleton()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new PluginRegistry();
		}
		return SINGLETON;
	}
	
	/**
	 * get info about the named plugin
	 * 
	 * @pre none
	 * @post returns info about the plugin or null if it was not found
	 * @param name
	 * @return
	 */
	public PluginInfo getPluginByName(String name)
	{
		for(PluginInfo p_info: plugins)
		{
			if (p_info.getPlugin_name().equals(name))
			{
				return p_info;
			}
		}
		return null;
	}
}
