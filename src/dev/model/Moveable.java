package dev.model;

/**
 * Moveable
 *
 * Description: Interface that gives a mobility property to the 
 * implementor object.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 19, 2016
 * @version v1.0
 */
public interface Moveable {
	
	/**
	 * Function that moves the object.
	 * 
	 * @param deltaX {@code deltaX} coordinate difference to move.
	 * @param deltaY {@code deltaY} coordinate difference to move.
	 */
	public void moveObj(float deltaX, float deltaY);
	
	
	/**
	 * Executes a user-defined propagation method.
	 */
	public void propagate();
}
