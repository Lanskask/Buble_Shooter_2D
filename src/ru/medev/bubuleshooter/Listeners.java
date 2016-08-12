package ru.medev.bubuleshooter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ru.medev.bubuleshooter.GamePanel.STATES;

public class Listeners implements KeyListener, 
	MouseListener, MouseMotionListener {

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		// UP, DOWN, LEFT, RIGHT, 
		if(key == KeyEvent.VK_W){
			Player.up = true;
		}
		if(key == KeyEvent.VK_S){
			Player.down = true;
		}
		if(key == KeyEvent.VK_A){
			Player.left = true;
		}
		if(key == KeyEvent.VK_D){
			Player.right = true;
		}
		if(key == KeyEvent.VK_SPACE){
			Player.isFiring = true;
		}
		// UP-LEFT, UP-RIGHT, DOWN-LEFT, DOWN-RIGHT
		// UP-LEFT: 
		//		key == KeyEvent.VK_W and key == KeyEvent.VK_D 
		if(key == KeyEvent.VK_ESCAPE){
			GamePanel.state = GamePanel.STATES.MENU;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		// UP, DOWN, LEFT, RIGHT, 
		if(key == KeyEvent.VK_W){
			Player.up = false;
		}
		if(key == KeyEvent.VK_S){
			Player.down = false;
		}
		if(key == KeyEvent.VK_A){
			Player.left = false;
		}
		if(key == KeyEvent.VK_D){
			Player.right = false;
		}
		// UP-LEFT, UP-RIGHT, DOWN-LEFT, DOWN-RIGHT
		// UP-LEFT: 
		//		key == KeyEvent.VK_W and key == KeyEvent.VK_D
		if(key == KeyEvent.VK_SPACE){
			Player.isFiring = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		GamePanel.mouseX = e.getX();
		GamePanel.mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		GamePanel.mouseX = e.getX();
		GamePanel.mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			GamePanel.player.isFiring = true;
			GamePanel.leftMouse = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			GamePanel.player.isFiring = false;
			GamePanel.leftMouse = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
