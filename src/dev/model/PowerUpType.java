package dev.model;

/**
 * PowerUpType
 *
 * Description: Power Up enumerations.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 22, 2016
 * @version v1.0
 */
public enum PowerUpType 
{
	SCORE_MULTIPLIER_2,
	SCORE_MULTIPLIER_5,
	EXTRA_HP,
	EXTRA_SPEED,
	SHIELD;
	
	public static PowerUpType rand()
	{
		switch((int) (Math.random() * 5))
		{
			case 0:
				return SCORE_MULTIPLIER_2;
			case 1:
				return SCORE_MULTIPLIER_5;
			case 2:
				return EXTRA_HP;
			case 3:
				return EXTRA_SPEED;
			default:
				return SHIELD;
		}
	}
}