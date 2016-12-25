package dev.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileManager
 *
 * Description: Manages file operations, keeps paths required to route any
 * picture and sound related operations. 
 * 
 * Maintains a highscore table that has size 10, updates accordingly.
 *
 * @author A. Burak Sahin
 * @author Melis Kizildemir
 * @date   Dec 19, 2016
 * @version v1.0
 */
public final class FileManager {
	
	/* Constants */
	private static final File HIGHSCORERS_FILE = new File("res/data/highscores.gal");
	public static final int NUMBER_OF_ENTRIES = 20;
	
	/* Instance Variables */
	private static FileManager instance = new FileManager();
	private String[] scorers;
	
	/**
	 * Don't let anyone instantiate this class
	 */
	private FileManager() {}
	
	/**
	 * @return the instance of the {@code FileManager} class to the caller.
	 */
	public static FileManager getInstance()
	{
		return instance;
	}
	
	/**
	 * Sets the specified scorer in its appropriate position in the highscores
	 * table.
	 * 
	 * @param name is the name of the scorer.
	 * @param score is the score he/she made.
	 */
	public void setScorer(String name, int score)
	{
		if(scorers == null)
		{
			createArray();
			writeFile();
		}
		
		for(int i = 1; i < scorers.length; i += 2)
			if(Integer.parseInt(scorers[i]) < score)
			{
				for(int j = scorers.length - 4; j >= i - 1; j -= 2)
				{
					scorers[j + 2] = scorers[j];
					scorers[j + 3] = scorers[j + 1];
				}
				
				scorers[i - 1] = name;
				scorers[i] = String.valueOf(score);
				writeFile();
				return;
			}
	}
	
	/**
	 * scorers = |__0__|__1__|__2__|__3__|__4__|__5__|..
	 * 	                 
	 * <p>
	 * As shown above, since the string array will be of even size, and 
	 * since we know that each entry contains a pair of <b> name </b> and
	 * <b>score</b> so we can safely assume that at each of 0, 2, 4... indicies 
	 * there will be a string which is the name of the scorer and at each of 
	 * 1, 3, 5... indicies there will be a number which is the score made by the
	 * user.   
	 * </p>
	 * 
	 * <p> That will be the basis of operations througout. </p>
	 */
	public void writeFile() 
	{
		StringBuilder builder = new StringBuilder();
		for(String str : scorers)
			builder.append(str).append(",");
		builder.deleteCharAt(builder.length() - 1);
		
		try(DataOutputStream output = new DataOutputStream(new FileOutputStream(HIGHSCORERS_FILE)))
		{
			output.writeChars(builder.toString());
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[FileManager] File " + HIGHSCORERS_FILE + " was not found " 
					+ "while writing.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[FileManager] Error while reading the file " + HIGHSCORERS_FILE);
		}
	}
	
	/**
	 * This function reads the file {@link #HIGHSCORERS_FILE} and tokenizes it  
	 * 
	 * @return
	 */
	public String[] readFile()
	{
		StringBuilder builder = new StringBuilder();
		
		try(DataInputStream input = new DataInputStream(new FileInputStream(HIGHSCORERS_FILE)))
		{
			while(true)
				builder.append(input.readChar());
		}
		catch (EOFException e) { } 
		catch (FileNotFoundException e1) 
		{
			createArray();
			writeFile();
			readFile();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
			System.err.println("[ERROR]:[FileManager] Error while reading the file " + HIGHSCORERS_FILE);  
		}
		
		return builder.toString().split(",");
	}
	
	/**
	 *  Creates a string with 10 entries each of which as name = "[NULL]" and score = "-1"
	 */
	private void createArray()
	{
		scorers = new String[NUMBER_OF_ENTRIES];
		for(int i = 0; i < NUMBER_OF_ENTRIES; i += 2)
		{
			scorers[i] = "[NULL]";
			scorers[i + 1] = String.valueOf(-1);
		}
	}
	
	/**
	 * Resets highscore table stored in file highscores.gal
	 */
	public void resetHighscoreTable()
	{
		createArray();
		writeFile();
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 */
	public static class SoundUtils
	{
		/**
		 * Absolute path of the theme song. 
		 */
		public static final String MENU_SOUND_PATH = "res/sound/tracks/theme/theme.wav";
		
		/**
		 * Absolute path of the sound for the menu widget switch
		 */
		public static final String MENU_SWITCH_SOUND_PATH = "res/sound/samples/menu_switch.wav";
		
		/**
		 * Absolute path of the sound for the {@code AlienSpaceship}'s laser.
		 */
		public static final String ALIEN_LASER_PATH = "res/sound/samples/laser/alien_laser.wav";
		
		/**
		 * Absolute path of the sound for the {@code UserSpaceship}'s laser.
		 */
		public static final String USER_LASER_1_PATH = "res/sound/samples/laser/laser_1.wav";
		
		/**
		 * Absolute path of the sound for the {@code UserSpaceship}'s laser.
		 */
		public static final String USER_LASER_2_PATH = "res/sound/samples/laser/laser_2.wav";
		
		/**
		 * Absolute path of the sound for the {@code UserSpaceship}'s laser.
		 */
		public static final String USER_LASER_3_PATH = "res/sound/samples/laser/laser_3.wav";
		
		/**
		 * Absolute path of the sound for {@code Money}.
		 */
		public static final String COIN_PATH = "res/sound/samples/powerUpsDowns/coin.wav";
		
		/**
		 * Absolute path of the sound for explosion effect.
		 */
		public static final String EXPLOSION_PATH = "res/sound/samples/powerUpsDowns/explosion.wav";
		
		/**
		 * Absolute path of the sound for the {@code PowerDown} effect.
		 */
		public static final String POWER_DOWN_PATH ="res/sound/samples/powerUpsDowns/power_down.wav";
		
		/**
		 * Absolute path of the sound for the {@code PowerUp} effect.
		 */
		public static final String POWER_UP_PATH = "res/sound/samples/powerUpsDowns/power_up.wav";
		
		/**
		 * Absolute path of the sound for the {@code UserSpaceship}'s destroy effect.
		 */
		public static final String SHIP_DESTROY_PATH = "res/sound/samples/powerUpsDowns/ship_destroy.wav";
		
		
		/**
		 * Don't let anyone instantiate this class
		 */
		private SoundUtils() {}
	}
	
	/**
	 * @author A. Burak Sahin
	 */
	public static class ImageUtils
	{
		/**
		 * Absolute path of the images for splash screen.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String INTRO_ICON_PATH = "res/graphics/animation/intro/intro";
		
		/**
		 * Absolute path of the image for the credits page.
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String CREDITS_IMAGE_PATH = "res/graphics/style/credits";
		
		/**
		 * Default image extension used in Intergalactica.
		 */
		public static final String DEFAULT_IMAGE_EXTENSION = ".png";
		
		/**
		 * Default widget font. 
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String DEFAULT_WIDGET_FONT_PATH = "res/graphics/style/fonts/Constantine.ttf";
		
		/**
		 * Logo of intergalactica
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String LOGO_PATH = "res/graphics/style/main_menu_logo";
		
		/**
		 * Background of main menu.
		 * For the extensions:
		 */
		public static final String MENU_BACKGROUND_PATH = "res/graphics/style/backgrounds/menu_bg.jpg";
		
		/**
		 * Background of the game panel.
		 */
		public static final String GAME_PANEL_BACKGROUND_PATH = "res/graphics/style/backgrounds/star_bg.png";
		 
		/**
		 * New game button.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String NEW_GAME_BUTTON_PATH = "res/graphics/style/buttons/main_menu_button_newGame";
		
		/**
		 * Highscores button.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String HIGHSCORES_BUTTON_PATH = "res/graphics/style/buttons/main_menu_button_highscores";
		
		/**
		 * Settings button.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String SETTINGS_BUTTON_PATH = "res/graphics/style/buttons/main_menu_button_settings";
		
		/**
		 * Credits button.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String CREDITS_BUTTON_PATH = "res/graphics/style/buttons/main_menu_button_credits";
		
		/**
		 * Exit button.
		 * For the extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String EXIT_BUTTON_PATH = "res/graphics/style/buttons/main_menu_button_exit";
		
		/**
		 * Toggle button.
		 * For extensions:
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String TOGGLE_BUTTON_PATH = "res/graphics/style/buttons/Toggle";
		
		/**
		 * The {@code Gem BonusItem}
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String GEM_PATH = "res/graphics"; //TODO doldur
		
		/**
		 * The {@code PowerUp BonusItem}
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String POWER_UP_PATH = "res/graphics";
		
		/**
		 * The {@code PowerDown BonusItem}
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String POWER_DOWN_PATH = "res/graphics";
		
		/**
		 * The {@code Bomb BonusItem}
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String BOMB_PATH = "res/graphics";
		
		/**
		 * The {@code UserSpaceship} image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String USER_SPACESHIP_PATH = "res/graphics/style/ships/user/defaultship";
		
		/**
		 * The {@code AlienSpaceship} Light image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String LIGHT_ALIEN_PATH = "res/graphics/style/ships/alien/alienship_small";
		
		/**
		 * The {@code AlienSpaceship} Medium image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String MEDIUM_ALIEN_PATH = "res/graphics/style/ships/alien/alienship_medium";
		
		/**
		 * The {@code AlienSpaceship} Heavy image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String HEAVY_ALIEN_PATH = "res/graphics/style/ships/alien/alienship_large";
		
		/**
		 * The {@code UserSpaceship} laser image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String USER_LASER = "res/graphics/style/ships/user/laserGreen";
		
		/**
		 * The {@code AlienSpaceship} laser image
		 * 
		 * @see #DEFAULT_IMAGE_EXTENSION
		 */
		public static final String ALIEN_LASER = "res/graphics/style/ships/alien/laserGreen";
		
		/**
		 * Don't let anyone instantiate this class
		 */
		private ImageUtils() {}
	}
}
