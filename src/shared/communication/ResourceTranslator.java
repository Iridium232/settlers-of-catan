package shared.communication;

import shared.definitions.ResourceType;

public  class ResourceTranslator 
{
	/**
	 * Translates resource enums to strings for server communication
	 * @param type
	 * @pre none
	 * @post the enum's string equivalent is returned
	 */
	public static String translate(ResourceType type)
	{
		String result = "";
		switch(type)
		{
		case WOOD:
			result = "wood";
			break;
		case SHEEP:
			result = "sheep";
			break;
		case BRICK:
			result = "brick";
			break;
		case ORE:
			result = "ore";
			break;
		case WHEAT:
			result = "wheat";
			break;
		default:
			result = "ERROR";
		}
		return result;
	}
}
