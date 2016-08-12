package ru.medev.bubuleshooter;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameBack {

	//Fields
	private Color color;
	
	// Constructors
	public GameBack(){
		color = Color.BLUE;
//		color = Color.YELLOW;
	}
	
	
	// Functions
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}

}
