package dev.model;

import javax.swing.ImageIcon;

/**
 * Spaceship
 *
 * Description: An abstract spaceship
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 24, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public abstract class Spaceship extends GameObject {

	/* Constants */
	protected static final int DAMAGE_FACTOR = 5;
	private static final float DEFAULT_AGILITY_RATE = 1f;
	private static final float DEFAULT_SPACESHIP_POWER_RATE = 1.5f; 
	private static final int MAX_HEALTH = 100;
	
	/* Variables */
	protected float agilityRate;
	protected float powerRate;
	protected int healthPoints;
	protected Gun gun;
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the {@code Spaceship}.
	 * @param y			y coordinate of the {@code Spaceship}.
	 * @param width		width of the {@code Spaceship}.
	 * @param height	height of the {@code Spaceship}.
	 * @param image		image of this {@code Spaceship}.
	 */
	public Spaceship(int x, int y, int width, int height, ImageIcon image)
	{
		super(x, y, width, height, image);
		gun = new Gun(this);
		agilityRate = DEFAULT_AGILITY_RATE;
		powerRate = DEFAULT_SPACESHIP_POWER_RATE;
		healthPoints = MAX_HEALTH;
	}
	
	/**
	 * Shoot functionality of this {@code Spaceship}.
	 * 
	 * @return an {@code Ammo} object that carries the damage along with it.
	 * When it collides with some other enemy spaceship, it deals its carried
	 * damage.
	 */
	public abstract Ammo shoot();
	
	/**
	 * This function changes the value of {@code healthPoints}
	 * 
	 * @param val the damage to be done.
	 */
	public void changeHP (int val)
	{
		healthPoints += val;
		if(healthPoints <= 0)
			destroy();
	}
	
	/**
	 * @return the amount of {@code healthPoints} remaining
	 * in this {@code Spaceship}
	 */
	public int getHP()
	{
		return healthPoints;
	}
	
	/**
	 * Sets the {@code BonusItem} property to this spaceship
	 * 
	 * @param item
	 */
	public void acquire(BonusItem item)
	{
		//TODO doldur
	}
}
