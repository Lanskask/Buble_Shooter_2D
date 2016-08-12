package ru.medev.bubuleshooter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
//import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.JPanel;

//import sun.security.mscapi.KeyStore.MY;

//import com.sun.media.sound.Toolkit;

//import com.sun.org.glassfish.gmbal.Description;

public class GamePanel extends JPanel implements Runnable{
	
	//	Field
	public static int WIDTH = 600; // 400
	public static int HEIGHT = 600; // 400
	
	public static int mouseX;
	public static int mouseY;
	public static boolean leftMouse;
	
	private Thread thread = new Thread(this);

	private BufferedImage image;
//	private GradientPaint g;
	private Graphics2D g;
	
	private int FPS;
	private double millisToFps;
	private long timerFPS;
	private int sleepTime;
	
//	private enum STATES {
	public static enum STATES {
		MENU,
		PLAY
	}
	
	public static STATES state = STATES.MENU;
	
	public static GameBack background;
	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	
	public static Wave wave;
	
	public static Menu menu;
	
	// Constructors
	public GamePanel(){
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
		addKeyListener(new Listeners());;
		addMouseMotionListener(new Listeners());
		addMouseListener(new Listeners());
	}
		
	//Functions
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		
		FPS = 30;
		millisToFps = 1000/FPS;
		sleepTime = 0;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g =(Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		String name = g.getClass().getName();
//		System.out.println(name); // name = sun.java2d.SunGraphics2D
//		sun.java2d.SunGraphics2D; // name
		
		leftMouse = false;
		background = new GameBack();
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		
		wave = new Wave();
		
		menu = new Menu();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		BufferedImage buffered = new BufferedImage(16,
				16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g3 = (Graphics2D) buffered.getGraphics();
		g3.setColor(new Color(255, 255, 255));
		g3.drawOval(0, 0, 4, 4);
		g3.drawLine(2, 0, 2, 4);
		g3.drawLine(0, 2, 4, 2);
		Cursor myCursor = kit.createCustomCursor(buffered, 
				new Point(3, 3), "myCursor");
		g3.dispose();
		
		// TODO Remove this new enemies
		// For testing with 

//		enemies.add(new Enemy(1, 1));
//		enemies.add(new Enemy(1, 1));
//		if (enemies.size() == 0 ) { // TODO This is mein - DontWork
//			enemies.add(new Enemy(1, 1));
//		}
				
		while(true){ // TODO States (Menu, Play)
			
			this.setCursor(myCursor);
			
			timerFPS = System.nanoTime();
			
			if (state.equals(STATES.MENU)) {
				background.update();
				background.draw(g);
				
				menu.update();
				menu.draw(g);
				gameDraw();
			}
			if (state.equals(STATES.PLAY)) {
//				long timer = System.nanoTime();
				gameUpdate();
				gameRender();
				gameDraw();
				
//				long elapsed = (System.nanoTime() - timer) / 1000000;
////				System.out.println(bullets.size());
//				System.out.println(elapsed);
			}
					

			
			timerFPS = ( System.nanoTime() - timerFPS ) /1000000;
//			System.out.println(timerFPS);
			if (millisToFps > timerFPS) {
				sleepTime = (int) (millisToFps - timerFPS);
			} else sleepTime = 1;
			
			try {
				thread.sleep(33); // TODO FPS
//				thread.sleep(sleepTime);
//				System.out.println(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			timerFPS = 0;
			sleepTime = 1;
		}	
	}
	
	/**
	 * @Description Where is the all data updating 
	 */
	public void gameUpdate() {
		// Background update
		background.update();
		
		// Player update
		player.update();
		
		// Bullet update
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			boolean remove = bullets.get(i).remove();
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		// Show the size of Bullets array
//		System.out.println("Number of bullets: " + bullets.size() );
		
		// Bullet update
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		
		// Bullets-enemy collide
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			double ex = e.getX();
			double ey = e.getY();
			
			for (int j = 0; j < bullets.size(); j++) {
				Bullet b = bullets.get(j);
				double bx = b.getX();
				double by = b.getY();
				
				double dx = ex - bx; // differense_x = enemy_x - bullet_x
				double dy = ey - by;// differense_x = enemy_x - bullet_x
				
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if ((int) dist <= e.getR() + b.getR()) {
					e.hit();
					bullets.remove(j);
					boolean remove = e.remove();
					if (remove){
						enemies.remove(i);
						i--;
						break;
					}
				}
			}			
		}
		
		// Enemy-player collide
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			double  ex = e.getX();
			double  ey = e.getY();
			
			double px = player.getX();
			double py = player.getY();
			int pr = player.getR();
			
			double dx = ex - px; // differense_x = enemy_x - bullet_x
			double dy = ey - py;// differense_x = enemy_x - bullet_x
			
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			if ((int) dist <= e.getR() + player.getR()) {
				e.hit();
				player.hit();
				
				boolean remove = e.remove();
				if (remove){
					enemies.remove(i);
					i--;
					break;
				}
			}
		}
		
		// Wave update
		wave.update();
	}
	
	/**
	 * @Description Where is the graphics data updating 
	 */
	public void gameRender() {
		// Background draw
		background.draw(g);
		
		// Player draw
		player.draw(g); 
		
		// Bullet draw
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		
		// Enemy draw
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//Wave draw
//		if (wave.showWave()) {
//			wave.draw(g);
//		}
		wave.draw(g);
	}
	
	private void gameDraw(){
//		Graphics2D g2 = this.getGraphics();
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
}
