package dev.model;

import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Ammo
 *
 * Description: An {@code Ammo} either shooted from Alien or User.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 24, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class Ammo extends GameObject {

	/* Constants */
	private static final int AMMO_SPEED = 5;
	
	/* Variables */
	private int damage;
	private boolean isAlienAmmo;
	 
	/**
	 * Constructor
	 * 
	 * @param isAlien {@code true} if this ammo was shot by an alien.
	 * @param damage the damage this ammo carries.
	 */
	public Ammo(boolean isAlien, float damage, Point p, ImageIcon image)
 	{
		super(p.x, p.y, Gun.ammoDimension.width, Gun.ammoDimension.height, image);
		isAlienAmmo = isAlien;
		this.damage = (int) damage;
		setRect(p.x, p.y, getWidth(), getHeight());
	}
	
	/**
	 * @return the damage this ammo carries.
	 */
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * @return {@code true} if this ammo was shot by an alien.
	 */
	public boolean isAlienAmmo()
	{
		return isAlienAmmo;
	}
	
	@Override
	public void propagate()
	{
		if(isAlienAmmo())
			moveObj(0, -1 * AMMO_SPEED);
		else
			moveObj(0, AMMO_SPEED);
	}
}
