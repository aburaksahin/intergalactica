package dev.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dev.model.GameObject;

/**
 * GameEntity 
 *
 * Description: Graphical representations of the {@code GameObject}s.
 * It also observes any chages
 *
 * @author A. Burak Sahin
 * @date   Dec 20, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class GameEntity extends JLabel implements Observer {

	/* Variables */
	private GameObject obj;
	
	/**
	 * Constructor
	 * 
	 * @param object 
	 */
	public GameEntity(GameObject object, ImageIcon icon)
	{
		super(icon);
		obj = object;
		setSize(new Dimension((int) obj.width , (int) obj.height));
	}
	

	@Override
	public void update(Observable o, Object arg) {
		setLocation((int) obj.x,(int) obj.y);
		System.out.println("update");
	}
	

}
