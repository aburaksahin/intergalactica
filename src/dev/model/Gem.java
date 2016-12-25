package dev.model;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * Gem
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @date   Dec 24, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class Gem extends BonusItem {

	/* Constants */
	private final int VALUE = (int) (Math.random() * 300);
	private static final ImageIcon GEM_IMAGE = new ImageIcon(FileManager.ImageUtils.GEM_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION);
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the object
	 * @param y			y coordinate of the object
	 * @param image		image of this {@code Gem}.
	 */
	public Gem(int x, int y) {
		super(x, y, GEM_IMAGE);
	}

	/**
	 * @return VALUE of this {@code Gem}
	 */
	public int getGemValue()
	{
		return VALUE;
	}
}
