package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	public static boolean[] keys;
	public static boolean up, down, left, right, i, k, j, l, w, a, s, d, e, o, q, u, space;
	
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public static void tick(){
		
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];	
		
		i = keys[KeyEvent.VK_I];
		k = keys[KeyEvent.VK_K];
		j = keys[KeyEvent.VK_J];
		l = keys[KeyEvent.VK_L];
		
		w = keys[KeyEvent.VK_W];
		a = keys[KeyEvent.VK_A];
		s = keys[KeyEvent.VK_S];
		d = keys[KeyEvent.VK_D];
		
		e = keys[KeyEvent.VK_E];
		o = keys[KeyEvent.VK_O];
		
		q = keys[KeyEvent.VK_Q];
		u = keys[KeyEvent.VK_U];

		space = keys[KeyEvent.VK_SPACE];
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		//System.out.println("Pressed!");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
}
