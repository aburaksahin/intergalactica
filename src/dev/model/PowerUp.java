package dev.model;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * PowerUp
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 20, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class PowerUp extends BonusItem {

	/* Constants */
	public final PowerUpType TYPE = PowerUpType.rand();
	private static final ImageIcon POWER_UP_IMAGE = new ImageIcon(FileManager.ImageUtils.POWER_UP_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION);
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the object
	 * @param y			y coordinate of the object
	 * @param image		image of this {@code PowerUp}.
	 */
	public PowerUp(int x, int y) {
		super(x, y, POWER_UP_IMAGE);
	}
}

