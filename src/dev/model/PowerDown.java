package dev.model;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * PowerDown
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 24, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class PowerDown extends BonusItem {

	/* Constants */
	public final PowerDownType TYPE = PowerDownType.rand();
	private static final ImageIcon POWER_DOWN_IMAGE = new ImageIcon(FileManager.ImageUtils.POWER_DOWN_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION);
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the object
	 * @param y			y coordinate of the object
	 * @param image		image of this {@code MoveableGameObject}.
	 */
	public PowerDown(int x, int y) {
		super(x, y, POWER_DOWN_IMAGE);
	}
}
