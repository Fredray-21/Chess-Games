package chess;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ChessGUI {

	public static JFrame frame;
	public static ChessMouseEvent chessMouseEvent;

	/**
	 * Create window and add the mouse listener to the frame
	 * 
	 * @param component
	 * @param frameName
	 */
	public static void showOnFrame(JComponent component, String frameName) {
		frame = new JFrame(frameName);
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		chessMouseEvent = new ChessMouseEvent();
		frame.addWindowListener(wa);
		frame.addMouseListener(chessMouseEvent);
		frame.addMouseMotionListener(chessMouseEvent);
		frame.getContentPane().add(component);
		frame.pack();
		frame.setVisible(true);
	}

	public static void setIconPath(String s) {
		frame.setIconImage(new ImageIcon(s).getImage());
	}

	/**
	 * Get graphic related to the window
	 * 
	 * @return Graphic2D context
	 */
	public java.awt.Graphics2D getGraphics() {
		return (Graphics2D) frame.getRootPane().getContentPane().getGraphics();
	}

	/**
	 * Get Chess mouse event object
	 * 
	 * @return ChessMouse event management
	 */
	public static ChessMouseEvent getChessMouseEvent() {
		return chessMouseEvent;
	}

	public static void setResizable(boolean resizable) {
		frame.setResizable(resizable);
	}
}
