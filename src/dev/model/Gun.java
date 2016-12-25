package dev.model;

import java.awt.Dimension;

/**
 * Gun
 *
 * Description: Gun that is attached in front of the
 * {@code Spaceship}.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 24, 2016
 * @version v1.0
 */
public class Gun {
	
	/* Constants */
	private static final float USER_POWER_RATE = 1.4f;
	private static final float ALIEN_POWER_RATE = 1f;
	
	/* Variables */
	public static Dimension ammoDimension;
	private float powerRate;
	
	/**
	 * Constructor
	 * 
	 * A gun that contains a power rate.
	 * 
	 * @param type
	 */
	public Gun(Spaceship type)
	{
		if(type.getClass() == AlienSpaceship.class)
		{
			powerRate = ALIEN_POWER_RATE;
			switch(((AlienSpaceship) type).getShipClass())
			{
				case AlienSpaceship.LIGHT_CLASS_SHIP:
					powerRate *= AlienSpaceship.LIGHT_CLASS_SHIP;
					ammoDimension = new Dimension(10, 20);
					break;
					
				case AlienSpaceship.MEDIUM_CLASS_SHIP:
					powerRate *= AlienSpaceship.MEDIUM_CLASS_SHIP;
					ammoDimension = new Dimension(20, 40);
					break;
					
				case AlienSpaceship.HEAVY_CLASS_SHIP:
					powerRate *= AlienSpaceship.HEAVY_CLASS_SHIP;
					ammoDimension = new Dimension(30, 60);
					break;
			}
		}
		else if(type.getClass() == UserSpaceship.class)
			powerRate = USER_POWER_RATE;
		else
			powerRate = 0;
	}
	
	/**
	 * @return the power rate of this {@code Gun}
	 */
	public float getPowerRate()
	{
		return powerRate;
	}
}
