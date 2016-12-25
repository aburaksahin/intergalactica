package dev.view;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dev.control.FileManager;
import dev.control.GameEngine;
import dev.control.SoundManager;
import dev.model.GameObject;

/**
 * ScreenController
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
public final class ScreenController {
	
	private static final int MAIN_MENU_PANEL = 1;
	private static final int GAME_VIEW_PANEL = 2;
	private static final int HIGHSCORES_PANEL = 3;
	private static final int PAUSE_MENU_PANEL = 4;
	private static final int SETTINGS_PANEL = 5;
	private static final int CREDITS_PANEL = 6;
	
	private static ScreenController instance;
	
	private SoundManager soundManager;  
	private MainFrame frame;
	private PauseMenuPanel pausePanel;
	private HighScoresPanel highscoresPanel;
	private SettingsPanel settingsPanel;
	private MainMenuPanel mainMenuPanel;
	private GamePanel gamePanel;
	private CreditsPanel creditsPanel;
	private BufferedImage backgroundImage;
	private JPanel currentScreen;
	private FileManager fileManager;
	private GameEngine gameEngine;
	private boolean goBackToPause;
	
	private ScreenController()
	{
		soundManager = SoundManager.getInstance();
		fileManager = FileManager.getInstance();
		backgroundImage = null;
		goBackToPause = false;
		
		try 
		{
			backgroundImage = ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[ScreenController] Failed to load background image.");
		}
		
		//--- Panels ---
		mainMenuPanel = new MainMenuPanel(backgroundImage, this);
		settingsPanel = new SettingsPanel(backgroundImage, this);
		highscoresPanel = new HighScoresPanel(backgroundImage, this);
		creditsPanel = new CreditsPanel(backgroundImage, this);
		gamePanel = new GamePanel(this);
		pausePanel = new PauseMenuPanel(backgroundImage, this);
		
		//Make every panel invisible and disabled, except the main menu
		settingsPanel.setVisible(false);
		settingsPanel.setEnabled(false);
		highscoresPanel.setVisible(false);
		highscoresPanel.setEnabled(false);
		creditsPanel.setVisible(false);
		creditsPanel.setEnabled(false);
		gamePanel.setVisible(false);
		gamePanel.setEnabled(false);
		pausePanel.setVisible(false);
		pausePanel.setEnabled(false);
		
		currentScreen = mainMenuPanel;
		
		//--- Frame ---
		frame = MainFrame.getInstance(new BasicPanel[] {pausePanel, creditsPanel, highscoresPanel, settingsPanel, mainMenuPanel}, gamePanel);
		frame.setVisible(true);
		
		soundManager.playBackground(FileManager.SoundUtils.MENU_SOUND_PATH);
		
		gameEngine = GameEngine.getInstance(); 
	}
	
	public static ScreenController getInstance()
	{
		if(instance == null)
			instance = new ScreenController();
		return instance;
	}
	
	public void initializeGUI()
	{
		frame.init();
	}
	
	public void fireLeftKey()
	{
		gameEngine.userSpaceshipGoLeft();
	}
	
	public void fireRightKey()
	{
		gameEngine.userSpaceshipGoRight();
	}
	
	public void fireShootKey()
	{
		gameEngine.userSpaceshipGoRight();
	}
	
	private void setCurrentPanel(int panelType)
	{
		currentScreen.setVisible(false);
		currentScreen.setEnabled(false);
		currentScreen.setFocusable(false);
		switch(panelType)
		{
			case MAIN_MENU_PANEL:
				currentScreen = mainMenuPanel;
				break;
			
			case GAME_VIEW_PANEL:
				currentScreen = gamePanel;
				break;
				
			case HIGHSCORES_PANEL:
				currentScreen = highscoresPanel;
				break;
				
			case PAUSE_MENU_PANEL:
				currentScreen = pausePanel;
				break;
				
			case SETTINGS_PANEL:
				if(currentScreen == pausePanel)
					goBackToPause = true;
				currentScreen = settingsPanel;
				break;
				
			case CREDITS_PANEL:
				currentScreen = creditsPanel;
				break;
				
			default:
				System.err.println("[ERROR]:[ScreenController] Panel type couldn not be resolved while assigning.");
				break;	
		}
		currentScreen.setEnabled(true);
		currentScreen.setVisible(true);
		currentScreen.setFocusable(true);
		currentScreen.requestFocusInWindow();
	}
	
	public void initGame()
	{
		soundManager.setMusic(false); //TODO sonra degistir
		setCurrentPanel(GAME_VIEW_PANEL);
		gameEngine.startGame();
	}
	
	public void addGameEntities(ArrayList<GameObject> entities)
	{
		for(GameObject obj : entities)
		{
			GameEntity en = new GameEntity(obj, obj.getImage());
			obj.addObserver(en);
			en.setBounds(obj.x, obj.y, obj.width, obj.height);
			gamePanel.add(en);
			gamePanel.revalidate();
			gamePanel.repaint();
		}
	}
	
	public void displaySettings()
	{
		setCurrentPanel(SETTINGS_PANEL);
	}
	
	public int getLeftKey()
	{
		return settingsPanel.getLeftKey();
	}
	
	public int getRightKey()
	{
		return settingsPanel.getRightKey();
	}
	
	public int getShootKey()
	{
		return settingsPanel.getShootKey();
	}
	
	public void playButtonSound()
	{
		soundManager.playSound(FileManager.SoundUtils.MENU_SWITCH_SOUND_PATH);
	}
	
	public void displayCredits()
	{
		setCurrentPanel(CREDITS_PANEL);
	}
	
	public void displayHighScores()
	{
		setCurrentPanel(HIGHSCORES_PANEL);
	}
	
	public void displayPauseMenu()
	{
		setCurrentPanel(PAUSE_MENU_PANEL);
	}
	
	public void displayMainMenu()
	{
		setCurrentPanel(MAIN_MENU_PANEL);
	}
	
	public void goBackWhenESC()
	{
		if(currentScreen == pausePanel)
			setCurrentPanel(GAME_VIEW_PANEL);
		else if(goBackToPause || currentScreen == gamePanel)
		{
			goBackToPause = false;
			setCurrentPanel(PAUSE_MENU_PANEL);
		}
		else
			setCurrentPanel(MAIN_MENU_PANEL);
	}
	
	public void setFrameLocation(int x, int y)
	{
		frame.setLocation(x, y);
	}
	
	public Point getFrameLocation()
	{
		return frame.getLocation();
	}
	
	public void signalShutdown()
	{
		int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", 
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(selection == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
	}
	
	public void signalIconify()
	{
		frame.setExtendedState(JFrame.ICONIFIED);
	}
	
	public void setMusicVolume(boolean state)
	{
		soundManager.setMusic(state);
	}
	
	public void setSoundVolume(boolean state)
	{
		soundManager.setSound(state);
	}
	
	public void setMusicVolume(float dB)
	{
		 soundManager.setMusicVolume(dB);
	}
	
	public void setSoundVolume(float dB)
	{
		 soundManager.setSoundVolume(dB);
	}
	
	public String[] getHighscorers()
	{
		return fileManager.readFile();
	}

	public void resetHighscores()
	{
		fileManager.resetHighscoreTable();
	}
	
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				ScreenController.getInstance();
			}
		});
	}
	
}
