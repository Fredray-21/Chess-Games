package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ChessMouseEvent implements MouseListener, MouseMotionListener {

	private boolean drag = false;
	private  boolean click = false;
	private int x = 0;
	private int y = 0;

	/**
	 * default constructor
	 */
	public ChessMouseEvent() {

	}

	// TODO: error position of the mouse car le get x et y prend en compte les bordures
	// 1. essaye de faire un get x et y qui prend en compte les bordures
	// 2. essaye de retirer les bordures
	// 3. J'ai mit pas defaut un getX/Y qui prend en compte les bordures (-7, -30)

	/**
	 * Get the last x location of the mouse pointer
	 * 
	 * @return
	 */
	public int getX() {
		return this.x-7;
	}

	/**
	 * Get the last y location of the mouse pointer
	 * 
	 * @return
	 */
	public int getY() {
		return this.y-30;
	}

	/**
	 * Tell us if the user has clicked
	 * 
	 * @return true on click otherwise false
	 */
	public boolean drag() {
		boolean status = drag;
		this.drag = false;
		return status;
	}

	public  boolean click() {
		boolean status = click;
		this.click = false;
		return status;
	}

	// ============================ Listener implementation
	// =========================

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a
	 * component.
	 * 
	 * @param[in] mouse event
	 */
	public void mouseClicked(MouseEvent mouse) {
		this.click = true;
	}

	/**
	 * implements MouseMotionListener
	 * 
	 * @param[in] mouse event
	 */
	public void mouseMoved(MouseEvent mouse) {
	}

	/**
	 * NOT USED - Called when the mouse enters in a component
	 * 
	 * @param[in] mouseEvent
	 */
	public void mouseEntered(MouseEvent mouse) {
	}

	/**
	 * NOT USED - Invoked when the mouse exits a component.
	 * 
	 * @param[in] mouseReleased
	 */
	public void mouseExited(MouseEvent mouse) {
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 * 
	 * @param[in] mouse event
	 */
	public void mouseReleased(MouseEvent mouse) {
		this.drag = false;
	}

	/**
	 * NOT USED - Invoked when a mouse button has been pressed on a component.
	 * 
	 * @param[in] mouse event
	 */
	public void mousePressed(MouseEvent mouse) {
		x = mouse.getX();
		y = mouse.getY();
		click = true;
	}

	/**
	 * implements MouseMotionListener
	 */
	public void mouseDragged(MouseEvent mouse) {
		x = mouse.getX();
		y = mouse.getY();
		drag = true;
	}
}
