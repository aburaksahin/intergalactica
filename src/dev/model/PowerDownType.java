package dev.model;

/**
 * PowerDownType
 *
 * Description: Power Down enumeration.
 *
 * @author A. Burak Sahin
 * @date   Dec 24, 2016
 * @version v1.0
 */
public enum PowerDownType {

	BOMB,
	X_INVERTER,
	MINUS_10_DAMAGE,
	MINUS_50_DAMAGE;
	
	public static PowerDownType rand()
	{
		switch((int) (Math.random() * 4))
		{
			case 0:
				return BOMB;
			case 1:
				return X_INVERTER;
			case 2:
				return MINUS_10_DAMAGE;
			default:
				return MINUS_50_DAMAGE;
		}
	}
}

