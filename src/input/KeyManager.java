package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	public static boolean[] keys;
	public static boolean up, down, left, right, numup, numdown, numleft, numright, num5, i, k, j, l, w, a, s, d, e, o, q, u, shift, space;
	
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public static void tick(){
		
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];	
		
		numup = keys[KeyEvent.VK_NUMPAD8];
		numdown = keys[KeyEvent.VK_NUMPAD2];
		numleft = keys[KeyEvent.VK_NUMPAD4];
		numright = keys[KeyEvent.VK_NUMPAD6];
		num5 = keys[KeyEvent.VK_NUMPAD5];
		
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
		
		shift = keys[KeyEvent.VK_SHIFT];

		space = keys[KeyEvent.VK_SPACE];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
}
