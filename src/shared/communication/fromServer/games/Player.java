package shared.communication.fromServer.games;

import client.data.RobPlayerInfo;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Player {
    public String color;
    private String name;
    private int id;

    public Player(String color, String name, int id) {
        this.color = color;
        this.name = name;
        this.id = id;
    }

    public Player(RobPlayerInfo victim) 
    {
		this.color = victim.getColor().name().toLowerCase();
		this.name = victim.getName();
		this.id = victim.getId();
	}

	public Player() 
	{
		
	}

	public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
