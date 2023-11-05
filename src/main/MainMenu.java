package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

/**
 * @author nawaz
 */
public class MainMenu extends JFrame implements Runnable {
	
	public Graphics2D g2;
	public KL keyListener = new KL();
	public ML mouseListener = new ML();
	public Text startGame, exitGame, pong;
	public boolean isRunning = true;
	
	public MainMenu() {
		this.setSize((int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT);
		this.setTitle(Constants.SCREEN_TITLE);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(keyListener);
		
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		
		this.startGame = new Text("Start Game", new Font("Times New Roman", Font.PLAIN, 40),
				Constants.SCREEN_WIDTH / 2.0 - 100.0, Constants.SCREEN_HEIGHT / 2.0, Color.WHITE);
		this.exitGame = new Text("Exit", new Font("Times New Roman", Font.PLAIN, 40),
				Constants.SCREEN_WIDTH / 2.0 - 50.0, Constants.SCREEN_HEIGHT / 2.0 + 60, Color.WHITE);
		this.pong = new Text("Pong", new Font("Times New Roman", Font.PLAIN, 100),
				Constants.SCREEN_WIDTH / 2.0 - 120.0, 150, Color.WHITE);
		
		this.g2 = (Graphics2D) getGraphics();
	}
	
	public void update(double dt) {
		Image dbImage = createImage(getWidth(), getHeight());
		Graphics dbg = dbImage.getGraphics();
		this.draw(dbg);
		g2.drawImage(dbImage, 0, 0, this);
		
		if(mouseListener.getMouseX() > startGame.x && mouseListener.getMouseX() < startGame.x + startGame.width &&
				mouseListener.getMouseY() > startGame.y - startGame.height / 2 &&
				mouseListener.getMouseY() < startGame.y + startGame.height / 2.0) {
			startGame.color = new Color(158, 158, 158);
			
			if(mouseListener.isMousePressed()) {
				Main.changeState(1);
			}
			
		} else {
			startGame.color = Color.WHITE;
		}
		
		if(mouseListener.getMouseX() > exitGame.x && mouseListener.getMouseX() < exitGame.x + exitGame.width &&
				mouseListener.getMouseY() > exitGame.y - exitGame.height / 2 &&
				mouseListener.getMouseY() < exitGame.y + exitGame.height / 2.0) {
			exitGame.color = new Color(158, 158, 158);
			
			if(mouseListener.isMousePressed()) {
				Main.changeState(2);
			}
			
		} else {
			exitGame.color = Color.WHITE;
		}
	}

	private void draw(Graphics dbg) {
		Graphics2D g2 = (Graphics2D) dbg;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, (int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT);
		
		startGame.draw(g2);
		exitGame.draw(g2);
		pong.draw(g2);
	}
	
	public void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		double lastFrameTime = 0.0;
		while(isRunning) {
			double time = Time.getTime();
			double deltaTime = time - lastFrameTime;
			lastFrameTime = time;
			
			update(deltaTime);
		}
		this.dispose();
		return;
	}
}
