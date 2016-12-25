package dev.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dev.control.FileManager;

/**
 * PauseMenuPanel
 *
 * Description: Pause Menu that pops when the user presses Escape key during 
 * game session.
 *
 * @author A. Burak Sahin
 * @date   Dec 19, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class PauseMenuPanel extends BasicPanel {

	private final Point TITLE_LOC = new Point(370, 50);
	private final Point RESUME_BUTTON_LOC = new Point(200, 400);
	private final Point SETTINGS_BUTTON_LOC = new Point(400,400);
	private final Point HOME_BUTTON_LOC = new Point(650, 400);
	
	private Widget title;
	private Widget resumeButton;
	private Widget settingsButton;
	private Widget homeButton;
	
	/**
	 * Constructor
	 * 
	 * @param background
	 * @param sc
	 */
	public PauseMenuPanel(BufferedImage background, ScreenController sc) {
		super(background, sc);
		
		try {
			title = new Widget("- Paused -", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) TITLE_LOC.getX(), (int) TITLE_LOC.getY(), true);
			
			resumeButton = new Widget("Resume", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) RESUME_BUTTON_LOC.getX(), (int) RESUME_BUTTON_LOC.getY(), true);
			
			settingsButton = new Widget("Settings", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) SETTINGS_BUTTON_LOC.getX(), (int) SETTINGS_BUTTON_LOC.getY(), true);
			
			homeButton = new Widget("Home", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) HOME_BUTTON_LOC.getX(), (int) HOME_BUTTON_LOC.getY(), true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[CreditsPanel] Error while loading images.");
		}
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					controller.goBackWhenESC();
			}
		});
		
		title.setFontColor(Color.WHITE);
		resumeButton.setFontColor(Color.WHITE);
		settingsButton.setFontColor(Color.WHITE);
		homeButton.setFontColor(Color.WHITE);
		
		title.resizeFont(30f);
		
		PauseMenuMouseListener listener = new PauseMenuMouseListener();
		resumeButton.addMouseListener(listener);
		settingsButton.addMouseListener(listener);
		homeButton.addMouseListener(listener);
		
		add(title);
		add(resumeButton);
		add(settingsButton);
		add(homeButton);
	}
	
	private class PauseMenuMouseListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) {
			Object source = e.getSource();
			if(source == resumeButton)
				controller.goBackWhenESC();
			else if(source == settingsButton)
				controller.displaySettings();
			else if(source == homeButton)
				controller.displayMainMenu();
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			controller.playButtonSound();
		}
	}
}
