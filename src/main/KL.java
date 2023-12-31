package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author nawaz
 */
public class KL implements KeyListener {
	
	private boolean[] keyPressed = new boolean[128];

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keyPressed[keyCode];
	}

}
