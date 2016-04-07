package server.plugin_attachments;

/**
 * Class Used to keep track of a plugin:
 * @author jeyrey
 *
 */
public class PluginInfo 
{
	private String main_class_name;
	private String jar_relative_uri;
	private String plugin_name;
	/**
	 * @return the main_class_name
	 */
	public String getMain_class_name() {
		return main_class_name;
	}
	/**
	 * @param main_class_name the main_class_name to set
	 */
	public void setMain_class_name(String main_class_name) {
		this.main_class_name = main_class_name;
	}
	/**
	 * @return the jar_relative_uri
	 */
	public String getJar_relative_uri() {
		return jar_relative_uri;
	}
	/**
	 * @param jar_relative_uri the jar_relative_uri to set
	 */
	public void setJar_relative_uri(String jar_relative_uri) {
		this.jar_relative_uri = jar_relative_uri;
	}
	/**
	 * @return the plugin_name
	 */
	public String getPlugin_name() {
		return plugin_name;
	}
	/**
	 * @param plugin_name the plugin_name to set
	 */
	public void setPlugin_name(String plugin_name) {
		this.plugin_name = plugin_name;
	}
	
	public String toString()
	{
		String result = "~~~~~~~~~~~~~~~~~~~~~~~`\nPlugin Info for: ";
		result += this.plugin_name;
		result += "\nMain class path: ";
		result += this.main_class_name;
		result += "\nPlugin Location: ";
		result += this.jar_relative_uri;
		result += "\n~~~~~~~~~~~~~~~~~~~~~~~";
		return result;
	}
}
