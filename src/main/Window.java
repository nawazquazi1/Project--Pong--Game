package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {
	
	public Graphics2D g2;
	public KL keyListener = new KL();
	public Rect playerOne;
	public Rect ai;
	public Rect ballRec;
	public PlayerController playerController;
	public AIController aiController;
	public Ball ball;
	public Text leftScoreText, rightScoreText;
	public boolean isRunning = true;
	
	public Window() {
		this.setSize((int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT);
		this.setTitle(Constants.SCREEN_TITLE);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(keyListener);
		
		Constants.TOOLBAR_HEIGHT = this.getInsets().top;
		Constants.INSETS_BOTTOM = this.insets().bottom;
		
		leftScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN,
				Constants.TEXT_SIZE), Constants.TEXT_X_POS, Constants.TEXT_Y_POS);
		rightScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN,
				Constants.TEXT_SIZE), Constants.SCREEN_WIDTH - Constants.TEXT_X_POS - Constants.TEXT_SIZE, Constants.TEXT_Y_POS);
		
		this.g2 = (Graphics2D) this.getGraphics();
		
		playerOne = new Rect(Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH,
				Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
		
		playerController = new PlayerController(playerOne, keyListener);
		
		ai = new Rect(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH - Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH,
				Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
		
		ballRec = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2,
				Constants.BALL_WIDTH, Constants.BALL_WIDTH, Constants.PADDLE_COLOR);
		
		ball = new Ball(ballRec, playerOne, ai, leftScoreText, rightScoreText);
		
		aiController = new AIController(new PlayerController(ai), ballRec);
	}
	
	public void update(double dt) {
		Image dbImage = createImage(getWidth(), getHeight());
		Graphics dbg = dbImage.getGraphics();
		this.draw(dbg);
		g2.drawImage(dbImage, 0, 0, this);
		
		
		playerController.update(dt);
		aiController.update(dt);
		ball.update(dt);
	}

	private void draw(Graphics dbg) {
		Graphics2D g2 = (Graphics2D) dbg;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, (int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT);
		
		leftScoreText.draw(g2);
		rightScoreText.draw(g2);
		
		playerOne.draw(g2);
		ai.draw(g2);
		ballRec.draw(g2);
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
