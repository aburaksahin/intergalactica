package dev.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * MainFrame
 *
 * Description: Constitutes the main frame of the game.
 *
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static MainFrame frame;
	
	/**
	 * Don't let anyone instantiate this class
	 * 
	 * @param contentPane a proxy content pane for this frame.
	 */
	private MainFrame(BasicPanel[] panels, GamePanel gamePanel)
	{
		setIconImage(new ImageIcon("res/graphics/style/icon_for_barx64.png").getImage());
		getContentPane().add(gamePanel);
		for(BasicPanel panel : panels)
			getContentPane().add(panel);
		setUndecorated(true);
		setPreferredSize(new Dimension(975, 620));
		pack();
		setResizable(false);
		setVisible(false);
		setLocationRelativeTo(null);
	}
	
	/**
	 * 
	 * @param pane
	 * @return
	 */
	public static MainFrame getInstance(BasicPanel[] panels, GamePanel gamePanel)
	{
		if(frame == null)
			frame = new MainFrame(panels, gamePanel);
		return frame;
	}
	
	/**
	 * 
	 */
	public void init()
	{
		if(frame != null)
			frame.setVisible(true);
		else
		{
			System.err.println("[ERROR]:[MainFrame] Frame is NULL!");
			System.exit(1);
		}
	}
	
	/**
	 * 
	 */
	public void terminate()
	{
		frame = null;
	}
}
