package dev.model;

import java.awt.Dimension;

import javax.swing.ImageIcon;

/**
 * BonusItem
 *
 * Description: An abstract bonus item that gets dropped when an alien dies.
 * It also contains its type
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 20, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public abstract class BonusItem extends GameObject {
	
	/* Constants */
	protected static final Dimension BONUS_DIMENSION = new Dimension(25, 25); 
	private static final float BONUS_SPEED = 10f;
	
	/* Variables */
	private int type;
	
	/**
	 * Constructor
	 * 
	 * @param x   		x coordinate of the object
	 * @param y			y coordinate of the object
	 * @param width		width of the object
	 * @param height	height of the object
	 * @param image		image of this {@code MoveableGameObject}.
	 */
	public BonusItem(int x, int y, ImageIcon image) {
		super(x, y, BONUS_DIMENSION.width, BONUS_DIMENSION.height, image);
	}

	public BonusItem returnRandom()
	{
		switch((int) (Math.random() * 4))
		{
			case 0:
				return new PowerUp(this.x, this.y);
			case 1:
				return new PowerDown(this.x, this.y);
			case 2:
				return new Gem(this.x, this.y);
			case 3:
				return new Bomb(this.x, this.y);
			default:
				return null;
		}
	}
	
	@Override
	public void moveObj(float deltaX, float deltaY) {
		super.moveObj(0, deltaY);
	}
	
	@Override
	public void propagate() {
		moveObj(0, BONUS_SPEED);
	}
	/**
	 * @return the type of this {@code BonusItem}.
	 */
	public int getType()
	{
		return type;
	}
}
