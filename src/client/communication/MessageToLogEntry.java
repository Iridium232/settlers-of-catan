package client.communication;

import client.control.Reference;
import shared.definitions.CatanColor;
import shared.model.messages.MessageLine;
import shared.model.player.Player;

public class MessageToLogEntry{
	private static MessageToLogEntry single;
	private MessageToLogEntry(){}
	public static MessageToLogEntry getMessageToLog(){
		if(single==null){
			single=new MessageToLogEntry();
			return single;
		}else{
			return single;
		}
	}
	public LogEntry convert(MessageLine line){
		LogEntry special=null;
		for(Player p:Reference.GET_SINGLETON().getFascade().getModel().getPlayers()){
			if(line.getSource().equals(p.getName())){
				special=new LogEntry(colorSwitch(p.getColor()),line.getMessage());
			}
		}
		return special;
	}
	
	private static CatanColor colorSwitch(String color){
		if(color.equals("red")){
			return CatanColor.RED;				
		} else if(color.equals("orange")){
			return CatanColor.ORANGE;
		} else if (color.equals("yellow")){
			return CatanColor.YELLOW;
		} else if (color.equals("blue")){
			return CatanColor.BLUE;
		} else if (color.equals("green")){
			return CatanColor.GREEN;
		} else if (color.equals("purple")){
			return CatanColor.PURPLE;
		} else if(color.equals("puce")){
			return CatanColor.PUCE;
		} else if(color.equals("white")){
			return CatanColor.WHITE;
		} else if(color.equals("brown")){
			return CatanColor.BROWN;
		} else return null;
	}
}
