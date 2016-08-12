package ru.medev.bubuleshooter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	
	//Fields
	private double x; // x_player_position
	private double y; // x_player_position
	private int r; // radius of player (circle)
	
	private double dx; // Move coeffitient
	private double dy;
	
	private int speed;
	private double health;
	
	private Color color1; // player_color 
	private Color color2; // perspective_player_color
	
	/**
	 * @Description 
	 * fields which answer for keys 
	 */
	public static boolean up; 
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static boolean isFiring;
	
	// Constructor
	public Player(){
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT * 3/4;
		r = 5;
		
		dx = 0;
		dy = 0;
		
		speed = 5;
		health = 2;
		
		color1 = Color.WHITE;
		color2 = Color.RED;
		
		// Initializating the pressing keys
		up = false;
		down = false;
		left = false;
		right = false;
		
		isFiring = false;
	}
	
	
	// Functions
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public int getR(){return r;}
	
	public void hit(){
		health--;
//		System.out.println(health);
	}
	
	public void update(){
		if( up && y > r){
//			y -= speed;
			dy = -speed;
		}
		if( down && y < GamePanel.HEIGHT){
//			y += speed;
			dy = speed;
		}
		if( left && x > r){
//			x -= speed;
			dx = -speed;
		}
		if( right && x < GamePanel.WIDTH){
//			x += speed;
			dx = speed;
		}
		if(up && left || up && right || down && left || down && right){
			double angle = Math.toRadians(45);
//			System.out.println(Math.sin(angle) + " " + Math.cos(angle));
			dy = dy * Math.sin(angle);
			dx = dx * Math.sin(angle);
		}
		
		y += dy; 
		x += dx;
		
		dx = 0;
		dy = 0;
		
		if(isFiring){
			GamePanel.bullets.add(new Bullet());
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color1);
		g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.darker());
		g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
	}
	
//	public void isFiring(){
//		
//	}
}
