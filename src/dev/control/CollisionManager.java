package dev.control;

import java.util.ArrayList;

import dev.model.GameObject;

/**
 * CollisionManager
 *
 * Description: CollisionManager constantly checks for the any
 * possible collisions.
 *
 * @author A. Burak Sahin
 * @author Melis Kizildemir
 * @date   Dec 21, 2016
 * @version v1.0
 */
public class CollisionManager {

	/* Variables */
	private static CollisionManager instance;
	
	private ArrayList<GameObject> entities;
	private GameEngine gameEngine;

	/**
	 * Constructor
	 * 
	 * @param engine  the reference of GameEngine to warn 
	 * about collisions.
	 */
	private CollisionManager(ArrayList<GameObject> list, GameEngine engine)
	{
		entities = list;
		gameEngine = engine;
	}
	
	/**
	 * Returns the only instance of this class.
	 * 
	 * @param list the list of {@code GameObject} entities in the game.
	 * @param engine  {@code GameEngine} reference.
	 * @return {@code this}, the instance of {@code CollisionManager}
	 */
	public static CollisionManager getInstance(ArrayList<GameObject> list, GameEngine engine)
	{
		if(instance == null)
			instance = new CollisionManager(list, engine);
		return instance;
	}
	
	/**
	 * Checks whether two {@code GameObject}s collide.
	 */
	public void checkCollisions()
	{
		for(GameObject first : entities)
			for(GameObject second : entities)
				if(first.intersects(second))
					gameEngine.checkActions(first, second);
	}
}
