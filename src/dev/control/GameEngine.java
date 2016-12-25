package dev.control;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import dev.model.AlienSpaceship;
import dev.model.Ammo;
import dev.model.BonusItem;
import dev.model.GameObject;
import dev.model.Spaceship;
import dev.model.UserSpaceship;
import dev.view.ScreenController;

/**
 * GameEngine
 *
 * Description: This class is the heart of the entire program. The code here
 * manages events that were stimulated by the view package elements and were 
 * responded by the model package elements. 
 *
 * @author A. Burak Sahin
 * @author Melis Kizildemir
 * @date   Dec 20, 2016
 * @version v1.0
 */
public class GameEngine {
	
	/* Constants */
	private static final Rectangle USER_SHIP_INITIAL_LOCATION = new Rectangle(); 
	
	/* Variables */
	private static GameEngine engine = new GameEngine();
	
	private int currentScore;
	private int highscore;
	private int currentLevel;
	private UserSpaceship userShip;
	private FileManager fileManager;
	private GameMapManager gameMapManager;
	private CollisionManager collisionManager;
	private SoundManager soundManager;
	private ScreenController screenController;
	private boolean pause;
	private Timer timer;
	
	/**
	 * Constructor
	 */
	private GameEngine()
	{
		pause = false;
		userShip = UserSpaceship.getInstance(USER_SHIP_INITIAL_LOCATION.x, USER_SHIP_INITIAL_LOCATION.y, USER_SHIP_INITIAL_LOCATION.width,
				USER_SHIP_INITIAL_LOCATION.height, new ImageIcon(FileManager.ImageUtils.USER_SPACESHIP_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
		soundManager = SoundManager.getInstance();
		
		gameMapManager = GameMapManager.getInstance();
		collisionManager = CollisionManager.getInstance(gameMapManager.getEntityList(), this);
		fileManager = FileManager.getInstance();
		screenController = ScreenController.getInstance();
		
		currentScore = 0;
		currentLevel = 1;
		highscore = Integer.parseInt(fileManager.readFile()[1]);
	}
	
	/**
	 * Plays alien's laser sound.
	 */
	public void playAlienLaserSound()
	{
		soundManager.playSound(FileManager.SoundUtils.ALIEN_LASER_PATH); 
	}
	
	/**
	 * Plays users laser sound
	 */
	public void playUserLaserSound()
	{
		soundManager.playSound(FileManager.SoundUtils.USER_LASER_3_PATH);
	}
	
	/**
	 * Moves the user ship a unit right
	 */
	public void userSpaceshipGoLeft()
	{
		userShip.moveObj(-1, 0);
	}
	
	/**
	 * Moves the user ship  unit to left
	 */
	public void userSpaceshipGoRight()
	{
		userShip.moveObj(1, 0);
	}
	
	/**
	 * Causes a shoot to occur
	 */
	public void userSpaceshpiShoot()
	{
		userShip.shoot();
	}
	
	/**
	 * Adds achieved points of the user
	 *  
	 * @param amount of points to be added
	 */
	public void setScore(int amount)
	{
		currentScore += amount;
	}
	
	/**
	 * @return the only instance of this class.
	 */
	public static GameEngine getInstance()
	{
		return engine;
	}

	/**
	 * Triggers the graphical representations of the 
	 */
	private void setGraphicalRepresentations()
	{
		screenController.addGameEntities(gameMapManager.getEntityList());
	}
	
	public void startGame()
	{
		gameMapManager.allocateAliens(currentLevel);
		setGraphicalRepresentations();
		timer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameMapManager.isLevelOver())
				{
					gameMapManager.moveEntities();
					collisionManager.checkCollisions();
				}
			}
		});
		timer.start();
			
		currentLevel++;
	}
	
	public void pauseGame()
	{
		pause = !pause;
	}
	
	
	/**
	 * Checks whether two collided objects share an interactive scenario.
	 * 
	 * @param obj1
	 * @param obj2
	 */
	public void checkActions(GameObject obj1, GameObject obj2)
	{
		if(obj1 instanceof Spaceship)
		{
			if(obj2 instanceof Spaceship)
				return;
			if(obj2 instanceof Ammo && !(obj1.getClass() == AlienSpaceship.class && ((Ammo) obj2).isAlienAmmo()))
			{
				((Spaceship) obj1).changeHP(-1 * ((Ammo) obj2).getDamage());
			}
			if(obj2 instanceof BonusItem)
			{
				((Spaceship) obj1).acquire((BonusItem) obj2);
				((BonusItem) obj2).destroy();
			}
		}
		else if(obj2 instanceof Spaceship)
		{
			if(obj1 instanceof Spaceship)
				return;
			if(obj1 instanceof Ammo && !(obj2.getClass() == AlienSpaceship.class && ((Ammo) obj1).isAlienAmmo()))
			{
				((Spaceship) obj2).changeHP(-1 * ((Ammo) obj1).getDamage());
				((Ammo) obj1).destroy();
			}
			if(obj1 instanceof BonusItem)
			{
				((Spaceship) obj2).acquire((BonusItem) obj1);
				((BonusItem) obj1).destroy();
			}
		}
	}
}
