package dev.model;

/**
 * Destroyable
 *
 * Description: {@code Destroyable} interface makes sure that its
 * will be guaranteed to cease to exist if called upon.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 23, 2016
 * @version v1.0
 */
public interface Destroyable {
	
	/**
	 * Destroys the object on which it was called.
	 */
	public void destroy();
}
