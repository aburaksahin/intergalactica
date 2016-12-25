package dev.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import dev.control.FileManager;

/**
 * MainMenuPanel
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends BasicPanel {
	
	/* Constants */
	private final int SHIFT_DELAY = 3;
	private final int DIM_DELAY = 5000;
	
	private final int TOTAL_WIDGETS = 5;
	private final int WIDGET_OFFSET = 200;
	private final Point LOGO_LOCATION = new Point(280, 70);
	private final Point NEW_GAME_LOCATION = new Point(60, 420); 
	private final Point HIGHSCORES_LOCATION = new Point(240, 420);
	private final Point SETTINGS_LOCATION = new Point(420, 420);
	private final Point CREDITS_LOCATION = new Point(600, 420);
	private final Point EXIT_LOCATION = new Point(780, 420);
	
	/* Variables */
	private Widget logo;
	private Widget newGame;
	private Widget highscores;
	private Widget settings;
	private Widget credits;
	private Widget exit;
	private Widget pointer;
	private int shift;
	private Timer shiftTimer;
	private Timer dimTimer;
	private Deque<Widget> items;
	
	/**
	 * 
	 * @param bgImage
	 * @param sc
	 */
	public MainMenuPanel(BufferedImage bgImage, ScreenController sc)
	{
		super(bgImage, sc);
		
		shift = WIDGET_OFFSET;
		pointer = null;
		items = new ArrayDeque<Widget>(TOTAL_WIDGETS);
		shiftTimer = new Timer(SHIFT_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(Widget w : items)
				{
					w.setLocation(w.getX(), w.getY() - 1);
					w.repaint();
				}
				if(shift <= 0)
				{
					shiftTimer.stop();
					dimTimer.start();
				}
				else
					shift--;
			}
		}); 
		
		dimTimer = new Timer(DIM_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(Widget w : items)
				{
					w.setSelected(false);
					w.setOpacity(0.3f);
					w.repaint();
				}
				dimTimer.stop();
			}
		});
			
		logo = null;  
		try 
		{
			logo = new Widget(ImageIO.read(new File(FileManager.ImageUtils.LOGO_PATH +
					FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)), FileManager.ImageUtils.LOGO_PATH, 
					(int) LOGO_LOCATION.getX(), (int) LOGO_LOCATION.getY()); 
			
			newGame = new MainMenuButtonWidget(ImageIO.read(new File(FileManager.ImageUtils.NEW_GAME_BUTTON_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)),
					FileManager.ImageUtils.NEW_GAME_BUTTON_PATH, (int) NEW_GAME_LOCATION.getX(), (int) NEW_GAME_LOCATION.getY());
			highscores = new MainMenuButtonWidget(ImageIO.read(new File(FileManager.ImageUtils.HIGHSCORES_BUTTON_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)),
					FileManager.ImageUtils.HIGHSCORES_BUTTON_PATH, (int) HIGHSCORES_LOCATION.getX(), (int) HIGHSCORES_LOCATION.getY());
			settings = new MainMenuButtonWidget(ImageIO.read(new File(FileManager.ImageUtils.SETTINGS_BUTTON_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)),
					FileManager.ImageUtils.SETTINGS_BUTTON_PATH, (int) SETTINGS_LOCATION.getX(), (int) SETTINGS_LOCATION.getY());
			credits = new MainMenuButtonWidget(ImageIO.read(new File(FileManager.ImageUtils.CREDITS_BUTTON_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)),
					FileManager.ImageUtils.CREDITS_BUTTON_PATH, (int) CREDITS_LOCATION.getX(), (int) CREDITS_LOCATION.getY());
			exit = new MainMenuButtonWidget(ImageIO.read(new File(FileManager.ImageUtils.EXIT_BUTTON_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)),
					FileManager.ImageUtils.EXIT_BUTTON_PATH, (int) EXIT_LOCATION.getX(), (int) EXIT_LOCATION.getY());
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[MainMenuPanel] Exception thrown while loading logo");
		}
		
		add(logo);
		
		//Exclude logo because it won't perform any animation.
		items.push(exit);
		items.push(credits);
		items.push(settings);
		items.push(highscores);
		items.push(newGame); 
		
		for(Widget w : items)
			add(w);
		
		addKeyListener(new MainMenuKeyListener());
		prepareAndStartAnimation();
	}
	
	/**
	 * Brings widgets into their initial condition.
	 */
	private void prepareAndStartAnimation()
	{	
		for(Widget w : items)
			w.setLocation(w.getX(), w.getY() + WIDGET_OFFSET);
		shiftTimer.start();
	}
	
	/**
	 * Resets every Widget's Opacity
	 */
	private void resetButtonOpacities()
	{
		for(Widget w : items)
		{
			w.setOpacity(-1f);
			w.repaint();
		}
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 *
	 */
	private class MainMenuKeyListener extends KeyAdapter
	{
		
		@Override
		public void keyPressed(KeyEvent e) 
		{
			resetButtonOpacities();
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				controller.playButtonSound();
				if (pointer == newGame)
				{
					dimTimer.stop();
					controller.initGame();
				}
				else if (pointer == highscores)
				{
					dimTimer.stop();
					controller.displayHighScores();
				}
				else if (pointer == settings)
				{
					dimTimer.stop();
					controller.displaySettings();
				}
				else if (pointer == credits)
				{
					dimTimer.stop();
					controller.displayCredits();
				}
				else if (pointer == exit)
				{
					controller.signalShutdown();
				}
			}
			else
				setWidgetSelected(e);
			dimTimer.restart();
		}
	}
	
	/**
	 * 
	 */
	private void setWidgetSelected(InputEvent e)
	{
		controller.playButtonSound();
		if(e instanceof MouseEvent)
			pointer = (Widget) e.getSource();
		else if(e instanceof KeyEvent)
		{
			if(((KeyEvent) e).getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(pointer == null)
					pointer = newGame;
				else
				{
					Iterator<Widget> it = items.iterator();
					while(it.hasNext())
					{
						if(it.next() == pointer)
						{
							if(it.hasNext())
								pointer = it.next();
							else
								pointer = newGame;
							break;
						}
					}
				}
			}
			else if(((KeyEvent) e).getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(pointer == null)
					pointer = exit;
				else
				{
					Iterator<Widget> it = items.descendingIterator();
					while(it.hasNext())
					{
						if(it.next() == pointer)
						{
							if(it.hasNext())
								pointer = it.next();
							else
								pointer = exit;
							break;
						}
					}
				}
			}
			else
				return;
		}
		else
			System.err.println("[ERROR]:[MainMenuKeyListener] Unresolvable type ID: " + e.getID());
		
		for(Widget w : items)
			w.setSelected(false);
		pointer.setSelected(true);
		
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 *
	 */
	private class MainMenuButtonWidget extends Widget
	{
		/**
		 * @param read
		 * @param newGameButtonPath
		 * @param x
		 * @param y
		 */
		public MainMenuButtonWidget(BufferedImage read, String newGameButtonPath, int x, int y) {
			super(read, newGameButtonPath, x, y);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			resetButtonOpacities();
			setWidgetSelected(e);
			dimTimer.stop();
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			pointer.setSelected(false);
			if(isFocusOwner())
				dimTimer.restart();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (pointer == newGame)
			{
				dimTimer.stop();
				controller.initGame();
			}
			else if (pointer == highscores)
			{
				dimTimer.stop();
				controller.displayHighScores();
			}
			else if (pointer == settings)
			{
				dimTimer.stop();
				controller.displaySettings();
			}
			else if (pointer == credits)
			{
				dimTimer.stop();
				controller.displayCredits();
			}
			else if (pointer == exit)
			{
				dimTimer.stop();
				controller.signalShutdown();
			}
		}
	}
}
