package dev.model;

import java.awt.Point;

import javax.swing.ImageIcon;

import dev.control.FileManager;

/**
 * AlienSpaceship
 *
 * Description: Constitutes an {@code AlienSpaceship} that is either 
 * of the class:
 * <ul>
 * <li>Light</li>
 * <li>Medium</li>
 * <li>Heavy</li>
 * <ul>
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 20, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class AlienSpaceship extends Spaceship {

	/* Constants */
	public static final int LIGHT_CLASS_SHIP = 1;
	public static final int MEDIUM_CLASS_SHIP = 2;
	public static final int HEAVY_CLASS_SHIP = 3;
	private static final float ALIEN_SPEED = 10f;
	
	/* Variables */
	private int shipClassFactor;
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the {@code AlienSpaceship}.
	 * @param y			y coordinate of the {@code AlienSpaceship}.
	 * @param width		width of the {@code AlienSpaceship}.
	 * @param height	height of the {@code AlienSpaceship}.
	 * @param icon		image of this {@code AlienSpaceship}.
	 * @param shipClas	class of this {@code AlienSpaceship} that has pivotal role in its power.
	 */
	private AlienSpaceship(int x, int y, int width, int height, ImageIcon icon, int classOfShip) {
		super(x, y, width, height, icon);
		shipClassFactor = classOfShip;
	}
	
	public AlienSpaceship(int x, int y, int classOfShip)
	{
		this(x, y, getWidthOfClass(classOfShip), getHeightOfClass(classOfShip), getImageOfClass(classOfShip), classOfShip);
	}

	@Override
	public Ammo shoot() {
		
		return new Ammo(true, gun.getPowerRate() * powerRate * DAMAGE_FACTOR * shipClassFactor, 
				new Point((int) (this.x + (this.width / 2 - Gun.ammoDimension.width / 2)), (int) this.height),
				new ImageIcon(FileManager.ImageUtils.ALIEN_LASER + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
	}

	@Override
	public void moveObj(float deltaX, float deltaY) {
		super.moveObj((this.x + deltaX) * agilityRate, (this.y + deltaY) * agilityRate);
	}
	
	/**
	 * @return the class of this {@code AlienSpaceship}.
	 */
	public int getShipClass()
	{
		return shipClassFactor;
	}
	
	@Override
	public void propagate()
	{
		switch((int) (Math.random() * 3))
		{
			case 1:
				if((int) (Math.random() * 2) == 1)
					moveObj(0, ALIEN_SPEED);
				else
					moveObj(ALIEN_SPEED, 0);
				break;
			case 2:
				if((int) (Math.random() * 2) == 1)
					moveObj(ALIEN_SPEED / 2, -1 * ALIEN_SPEED / 2);
				else
					moveObj(ALIEN_SPEED / 2, ALIEN_SPEED / 2);
				break;
			case 3:
				if((int) (Math.random() * 2) == 1)
					moveObj(ALIEN_SPEED * -1 / 2, ALIEN_SPEED / 2);
				else
					moveObj(ALIEN_SPEED * -1 / 2, ALIEN_SPEED * -1 / 2);
				break;
		}
	}
	
	private static int getWidthOfClass(int classOfShip)
	{
		switch(classOfShip)
		{
			case 1:
				return 30;
			case 2:
				return 60;
			default:
				return 100;
		}
	}
	
	private static int getHeightOfClass(int classOfShip)
	{
		switch(classOfShip)
		{
			case 1:
				return 66;
			case 2:
				return 82;
			default:
				return 137;
		}
	}
	
	private static ImageIcon getImageOfClass(int classOfShip)
	{
		String path = "";
		switch(classOfShip)
		{
			case 1:
				path = FileManager.ImageUtils.LIGHT_ALIEN_PATH;
				break;
			case 2:
				path = FileManager.ImageUtils.MEDIUM_ALIEN_PATH;
				break;
			case 3:
				path = FileManager.ImageUtils.HEAVY_ALIEN_PATH;
		}
		
		return new ImageIcon(path);
	}
}
