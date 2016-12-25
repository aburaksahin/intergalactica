package dev.model;

import java.awt.Point;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * UserSpaceship
 *
 * Description: The {@code Spaceship} that user controls.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 20, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class UserSpaceship extends Spaceship {

	/* Variables */
	private static UserSpaceship instance;
	
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the {@code UserSpaceship}.
	 * @param y			y coordinate of the {@code UserSpaceship}.
	 * @param width		width of the {@code UserSpaceship}.
	 * @param height	height of the {@code UserSpaceship}.
	 * @param image		image of this {@code UserSpaceship}.
	 */
	private UserSpaceship(int x, int y, int width, int height, ImageIcon image) 
	{
		super(x, y, width, height, image);
	}
	
	@Override
	public void moveObj(float deltaX, float deltaY) {
		super.moveObj((this.x + deltaX) * agilityRate, this.y);
	}
	
	/**
	 * @return only instance of this class.
	 */
	public static UserSpaceship getInstance(int x, int y, int width, int height, ImageIcon image)
	{
		if(instance == null)
			instance = new UserSpaceship(x, y, width, height, image);
		return instance;
	}
	
	@Override
	public Ammo shoot() {
		
		return new Ammo(false, gun.getPowerRate() * powerRate * DAMAGE_FACTOR, 
				new Point((int) (this.x + this.width / 2 + Gun.ammoDimension.width / 2), (int) (this.y + Gun.ammoDimension.height)), 
				new ImageIcon(FileManager.ImageUtils.USER_LASER + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
	}

	@Override
	public void propagate() {}
}
