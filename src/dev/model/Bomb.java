package dev.model;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * Bomb
 *
 * Description: Bomb {@code GameObject}. Damages any spaceship it encounters
 * including the aliens. It only drops when an alien gets killed.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 23, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class Bomb extends BonusItem {

	/* Constants */
	public final int DAMAGE = (int) (Math.random() * 51);
	private static final ImageIcon BOMB_IMAGE  = new ImageIcon(FileManager.ImageUtils.BOMB_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION);
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the object
	 * @param y			y coordinate of the object
	 */
	public Bomb(int x, int y) {
		super(x, y, BOMB_IMAGE);
	}
}
