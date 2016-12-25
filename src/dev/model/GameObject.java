package dev.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import dev.view.BasicPanel;

/**
 * GameObject
 *
 * Description: Abstract in-game object.
 *
 * @author A. Burak Sahin
 * @author Selin Erdem
 * @date   Dec 19, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public abstract class GameObject extends Rectangle implements Destroyable, Moveable {
	
	/* Variables */
	protected ImageIcon currentIcon;
	protected ObservableGameObject observable = new ObservableGameObject();

	/**
	 * Constructor for the {@code GameObject} 
	 * 
	 * @param x			x coordinate of this object
	 * @param y			y coordinate of this object
	 * @param width		width of this object
	 * @param height	height of this object
	 */
	public GameObject(int x, int y, int width, int height, ImageIcon icon)
	{
		super(x, y, width, height);
		currentIcon = icon;
	}
	
	/**
	 * Overload of the constructor {@link #GameObject(float, float, float, float, Image)}
	 * which sets image as null.
	 * @param rect that specifies dimensions and position.
	 */
	public GameObject(Rectangle rect)
	{
		this(rect.x, rect.y, rect.width, rect.height, null);
	}
	
	/**
	 * Overload of the constructor {@link #GameObject(float, float, float, float, ImageIcon)}
	 * 
	 * @param rect  rectangle that specifies dimensions and position.
	 * @param image image to be drawn.
	 */
	public GameObject(Rectangle rect, ImageIcon icon)
	{
		this(rect.x, rect.y, rect.width, rect.height, icon);
	}
	
	@Override
	public void destroy() {
		//TODO do something different
	}

	/**
	 * 
	 * @param newImage
	 */
	public void updateImage(ImageIcon newIcon)
	{
		currentIcon = newIcon;
	}
	
	/**
	 * @return {@code true} if this game object is image based.
	 */
	public boolean isImageBased()
	{
		System.out.println("image not null?");
		return currentIcon != null;
	}
	
	/**
	 * @return the image of this {@code GameObject}
	 */
	public ImageIcon getImage()
	{
		return currentIcon;
	}
	
	@Override
	public void moveObj(float deltaX, float deltaY) {
		float xVal = this.x + deltaX;
		if(!(xVal < BasicPanel.PANEL_DIMENSION.getMinX() && BasicPanel.PANEL_DIMENSION.getMaxX() < xVal))
			return;
		float yVal = this.y + deltaY;
		if(yVal > BasicPanel.PANEL_DIMENSION.getMaxY() || yVal < BasicPanel.PANEL_DIMENSION.getMinY())
			setRect(BasicPanel.PANEL_DIMENSION.getX() + BasicPanel.PANEL_DIMENSION.getWidth() / 2, 
					this.height + BasicPanel.PANEL_DIMENSION.getY(), this.width, this.height);
		else
			setRect(this.x + deltaX, this.y + deltaY, this.width, this.height);
		observable.notifyObservers(this);
	}
	
	@Override 
	public void propagate(){
		observable.notifyObservers(this);
	}
	
	/**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param   o   an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
	public synchronized void addObserver(Observer o) {
		
		observable.addObserver(o);
	}

	/**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     * @param   o   the observer to be deleted.
     */
	public synchronized void deleteObserver(Observer o) {
		
		observable.deleteObserver(o);
	}

	/**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#hasChanged()
     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
	protected void notifyObservers() {
		
		observable.notifyObservers();
	}

	 /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to indicate
     * that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and the <code>arg</code> argument.
     *
     * @param   arg   any object.
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#hasChanged()
     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
	protected void notifyObservers(Object arg) {
		
		observable.notifyObservers(arg);
	}

	/**
     * Clears the observer list so that this object no longer has any observers.
     */
	public synchronized void deleteObservers() {
		
		observable.deleteObservers();
	}

	/**
     * Tests if this object has changed.
     *
     * @return  <code>true</code> if and only if the <code>setChanged</code>
     *          method has been called more recently than the
     *          <code>clearChanged</code> method on this object;
     *          <code>false</code> otherwise.
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#setChanged()
     */
	public synchronized boolean hasChanged() {
		
		return observable.hasChanged();
	}

	/**
     * Returns the number of observers of this <tt>Observable</tt> object.
     *
     * @return  the number of observers of this object.
     */
	public synchronized int countObservers() {
		
		return observable.countObservers();
	}
	
	/**
	 * ObservableGameObject
	 * 
	 * Description: this class extends the functionality of being an Observable, 
	 * which, as an instance, gives {@code GameObject}s a property of
	 * being observed. 
	 *  
	 * @author A. Burak Sahin
	 */
	protected class ObservableGameObject extends Observable {}
}
