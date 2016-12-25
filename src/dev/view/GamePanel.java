package dev.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.control.FileManager;

/**
 * GamePanel
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @date   Dec 19, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener { 

	private static final BufferedImage BACKGROUND_IMAGE;
	private static final Rectangle PANEL_DIMENSION = new Rectangle(0, 0, 975, 620);
	
	static {
		BufferedImage temp = null;
		try 
		{
			temp = ImageIO.read(new File(FileManager.ImageUtils.GAME_PANEL_BACKGROUND_PATH));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[GamePanel] Failed to load the star background.");
		}
		BACKGROUND_IMAGE = temp;
	}
	
	private JLabel highscoreLabel; 
	private JLabel currentScoreLabel;
	private ScreenController controller;
	
	public GamePanel(ScreenController controller)
	{
		this.controller = controller;
		String highestScore = controller.getHighscorers()[1];
		highscoreLabel = new JLabel(" ");
		currentScoreLabel = new JLabel("");
		
		setLayout(null);
		setBounds((int) PANEL_DIMENSION.getX(), (int) PANEL_DIMENSION.getY(), (int) PANEL_DIMENSION.getWidth(), (int) PANEL_DIMENSION.getHeight());
		
		highscoreLabel.setForeground(Color.WHITE);
		currentScoreLabel.setBounds(0, 0, highestScore.length(), 15);
		
		currentScoreLabel.setForeground(Color.WHITE);
		currentScoreLabel.setBounds(getX(), getY(), 300, 300);
		
		add(highscoreLabel);
		add(currentScoreLabel);
		addKeyListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gra = (Graphics2D) g;
		
		//Allow antialiasing and rendering for High-Definition.
		gra.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gra.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gra.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		gra.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gra.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		gra.drawImage(BACKGROUND_IMAGE, getX(), getY(), this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ESCAPE)
			controller.displayPauseMenu();
		else if(keyCode == controller.getLeftKey())
			controller.fireLeftKey();
		else if(keyCode == controller.getRightKey())
			controller.fireRightKey();
		else if(keyCode == controller.getShootKey())
			controller.fireShootKey();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
