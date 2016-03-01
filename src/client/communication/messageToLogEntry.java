package client.communication;

import client.control.Reference;
import shared.definitions.CatanColor;
import shared.model.messages.MessageLine;
import shared.model.player.Player;

public class messageToLogEntry {
	private static messageToLogEntry single;
	private messageToLogEntry(){}
	public messageToLogEntry getMessageToLog(){
		if(single==null){
			single=new messageToLogEntry();
			return single;
		}else{
			return single;
		}
	}
	public LogEntry convert(MessageLine line){
		LogEntry special=null;
		for(Player p:Reference.GET_SINGLETON().getFascade().getModel().getPlayers()){
			if(line.getSource()==p.getName()){
				special=new LogEntry(colorSwitch(p.getColor()),line.getMessage());
			}
		}
		return special;
	}
	
	private CatanColor colorSwitch(String color){
		if(color=="red"){
			return CatanColor.RED;				
		} else if(color=="orange"){
			return CatanColor.ORANGE;
		} else if (color=="yellow"){
			return CatanColor.YELLOW;
		} else if (color=="blue"){
			return CatanColor.BLUE;
		} else if (color=="green"){
			return CatanColor.GREEN;
		} else if (color=="purple"){
			return CatanColor.PURPLE;
		} else if(color =="puce"){
			return CatanColor.PUCE;
		} else if(color=="white"){
			return CatanColor.WHITE;
		} else if(color=="brown"){
			return CatanColor.BROWN;
		} else return null;
	}
}
//RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN
