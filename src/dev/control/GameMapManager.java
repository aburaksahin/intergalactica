package dev.control;

import java.util.ArrayList;

import dev.model.AlienSpaceship;
import dev.model.GameObject;
import dev.model.UserSpaceship;
import dev.view.BasicPanel;

/**
 * GameMapManager
 *
 * Description: GameMapManager allocates, deallocates, moves aliens around
 * the game map. When an alien dies and gets hit, it gets removed from here
 * therefore destroy method actually deletes the alien from the {@code aliens}
 * list 
 *
 * @author A. Burak Sahin
 * @author Melis Kizildemir
 * @date   Dec 21, 2016
 * @version v1.0
 */
public class GameMapManager {

	/* Variables */
	private static GameMapManager instance;
	
	private ArrayList<GameObject> entities;
	
	/**
	 * Constructor
	 * 
	 * @param list of {@code GameObjects}
	 */
	private GameMapManager()
	{
		entities = new ArrayList<GameObject>();
	}
	
	/**
	 * @return the mere instance of this class.
	 */
	public static GameMapManager getInstance()
	{
		if(instance == null)
			instance = new GameMapManager();
		return instance;
	}
	
	/**
	 * @return list of {@code GameObjects} in the game.
	 */
	public ArrayList<GameObject> getEntityList()
	{
		return entities;
	}
	
	public void allocateAliens(int level)
	{
		if(level < 5) 
		{
			for(int i = 0; i < level * 5; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.LIGHT_CLASS_SHIP));
		}
		else if(level > 5 && level < 10) 
		{
			for(int i = 0; i < level; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.LIGHT_CLASS_SHIP));
			for(int i = 0; i < level; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.MEDIUM_CLASS_SHIP));
		}
		else
		{
			for(int i = 0; i < level; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.LIGHT_CLASS_SHIP));
			for(int i = 0; i < level; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.MEDIUM_CLASS_SHIP));
			for(int i = 0; i < level / 5; i++)
				entities.add(new AlienSpaceship(BasicPanel.PANEL_DIMENSION.x + BasicPanel.PANEL_DIMENSION.width / 2, 
						BasicPanel.PANEL_DIMENSION.y + 5, AlienSpaceship.HEAVY_CLASS_SHIP));
		}
	}
	
	/**
	 * @return a {@code boolean} value that is {@code true} if the only remaining object is the {@code UserSpaceship}.
	 */
	public boolean isLevelOver()
	{
		return (entities.size() == 1) && (entities.get(0).getClass() == UserSpaceship.class);
	}
	
	/**
	 * Moves entities to predefined routes
	 */
	public void moveEntities()
	{
		for(GameObject obj : entities)
			obj.propagate();
	}
}
