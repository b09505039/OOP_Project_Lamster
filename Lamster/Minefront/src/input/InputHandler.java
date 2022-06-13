package input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseMotionListener, MouseListener, FocusListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX; 		//d = drag
	public static int MouseDY;
	public static int MousePX;
	public static int MousePY; 		//pressed
	public static int MouseButton;
	public static boolean dragged = false;

	boolean forward, back, left, right, rleft, rright, jump, crouch, run, stop = false;

	public void tick() {		//detect the changes every second
		forward = key[KeyEvent.VK_W];
		back = key[KeyEvent.VK_S];
		left = key[KeyEvent.VK_A];
		right = key[KeyEvent.VK_D];
		rleft = key[KeyEvent.VK_LEFT];
		rright = key[KeyEvent.VK_RIGHT];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_CONTROL];
		run = key[KeyEvent.VK_SHIFT];
		stop = key[KeyEvent.VK_P];
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {	//when mouse is pressed
		MouseButton = e.getButton();
		MousePX = e.getX();						//get its x value
		MousePY = e.getY();						//get its y value

	}

	public void mouseReleased(MouseEvent e) {

		dragged = false;						//when user release the button, turn boolean dragged to false
		MouseButton = 0;						//when user release the button, turn MouseButton to 0

	}

	public void mouseDragged(MouseEvent e) {	//when mouse is dragged

		MouseDX = e.getX();						//get its x value
		MouseDY = e.getY();						//get its y value
		dragged = true;							//change boolean dragged to true
	}

	@Override
	public void mouseMoved(MouseEvent e) {		//when mouse is moved

		MouseX = e.getX();						//get its x value
		MouseY = e.getY();						//get its y value

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();				//keyCode is the which button player presses
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;					//and turn that keycode in key array to true
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();				//keyCode is the which button player presses
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;					//and turn that keycode in key array to false
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
