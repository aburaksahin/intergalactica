package dev.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import dev.control.FileManager;

/**
 * SettingsPanel
 *
 * Description: {@code SettingsPanel} contains the sound and control key settings of the game. 
 * 
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class SettingsPanel extends BasicPanel {
	
	private final int VOLUME_MAX = 100;
	private final int VOLUME_MIN = 0;

	private final Point MUSIC_LABEL_LOC = new Point(150, 110);
	private final Point SOUND_LABEL_LOC = new Point(150, 210);
	private final Point SETTINGS_LABEL_LOC = new Point(50, 20);
	private final Point CONTROLS_LABEL_LOC = new Point(60, 320);
	private final Point RIGHT_KEY_WIDGET_LOC = new Point(620, 500); 
	private final Point	LEFT_KEY_WIDGET_LOC = new Point(80, 400);
	private final Point SHOOT_KEY_WIDGET_LOC = new Point(350, 450);
	private final Point MUSIC_SLIDER_LOC = new Point(650, 110);
	private final Point SOUND_SLIDER_LOC = new Point(650, 210);
	private final Point MUSIC_TOGGLE_BUTTON_LOC = new Point(450, 95);
	private final Point SOUND_TOGGLE_BUTTON_LOC = new Point(450, 195);
	
	private JSlider musicVolume;
	private JSlider soundVolume;
	private Widget musicLabel;
	private Widget soundLabel;
	private JToggleButton muteSound;
	private JToggleButton muteMusic;
	private int rightKey;
	private int leftKey;
	private int shootKey;
	private Widget settingsLabel;
	private Widget controlsLabel;
	private Widget rightKeyLabel;
	private Widget leftKeyLabel;
	private Widget shootKeyLabel;
	private Widget pointer;
	
	/**
	 * @param background
	 * @param sc
	 */
	public SettingsPanel(BufferedImage background, ScreenController sc) {
		super(background, sc);
		
		rightKey = KeyEvent.VK_RIGHT;
		leftKey  = KeyEvent.VK_LEFT;
		shootKey = KeyEvent.VK_CONTROL;
		
		try 
		{
			musicLabel = new Widget("Music Volume", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) MUSIC_LABEL_LOC.getX(), (int) MUSIC_LABEL_LOC.getY(), true);
			soundLabel = new Widget("Sound Volume", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) SOUND_LABEL_LOC.getX(), (int) SOUND_LABEL_LOC.getY(), true);
			settingsLabel = new Widget("- Settings -", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) SETTINGS_LABEL_LOC.getX(), (int) SETTINGS_LABEL_LOC.getY(), true);
			controlsLabel = new Widget("- Controls -", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) CONTROLS_LABEL_LOC.getX(), (int) CONTROLS_LABEL_LOC.getY(), true);
			
			/*
			 * Override key to listen mouse events for reassigning key bindings.
			 */
			rightKeyLabel = new Widget("Right Key: " + KeyEvent.getKeyText(rightKey), ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) RIGHT_KEY_WIDGET_LOC.getX(), (int) RIGHT_KEY_WIDGET_LOC.getY(), true)
					{
						@Override
						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							
							Graphics2D gra = (Graphics2D) g;
							Stroke defaultStroke = gra.getStroke();
							if(pointer == this)
								gra.setStroke(new BasicStroke(7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							else
								gra.setStroke(new BasicStroke(3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							gra.setColor(getBackground());
							gra.drawRect(0, 0, getWidth(), getHeight());
							gra.setStroke(defaultStroke);
						}
						
					};
					
			leftKeyLabel = new Widget("Left Key: " + KeyEvent.getKeyText(leftKey), ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) LEFT_KEY_WIDGET_LOC.getX(), (int) LEFT_KEY_WIDGET_LOC.getY(), true)
					{
						@Override
						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							
							Graphics2D gra = (Graphics2D) g;
							Stroke defaultStroke = gra.getStroke();
							
							if(pointer == this)
								gra.setStroke(new BasicStroke(7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							else
								gra.setStroke(new BasicStroke(3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							gra.setColor(getBackground());
							gra.drawRect(0, 0, getWidth(), getHeight());
							gra.setStroke(defaultStroke);
						}
					};
					
			shootKeyLabel = new Widget("Shoot Key:" + KeyEvent.getKeyText(shootKey), ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) SHOOT_KEY_WIDGET_LOC.getX(), (int) SHOOT_KEY_WIDGET_LOC.getY(), true)
					{
						@Override
						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							
							Graphics2D gra = (Graphics2D) g;
							Stroke defaultStroke = gra.getStroke();
							
							if(pointer == this)
								gra.setStroke(new BasicStroke(7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							else
								gra.setStroke(new BasicStroke(3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
							gra.setColor(getBackground());
							gra.drawRect(0, 0, getWidth(), getHeight());
							gra.setStroke(defaultStroke);
						}
					};
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[SettingsPanel] Error while uploading images to widgets.");
		}
		
		controlsLabel.setFontColor(Color.WHITE);
		rightKeyLabel.setFontColor(Color.WHITE);
		leftKeyLabel.setFontColor(Color.WHITE);
		shootKeyLabel.setFontColor(Color.WHITE);
		settingsLabel.setFontColor(Color.WHITE);
		musicLabel.setFontColor(Color.WHITE);
		soundLabel.setFontColor(Color.WHITE);
		
		controlsLabel.resizeFont(26f);
		settingsLabel.resizeFont(26f); 
		
		muteSound = new JToggleButton(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "ON" +FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
		muteMusic = new JToggleButton(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "ON" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
		
		muteSound.setSelected(true);
		muteMusic.setSelected(true);
		muteSound.setBounds(new Rectangle(SOUND_TOGGLE_BUTTON_LOC, new Dimension(125, 60)));
		muteMusic.setBounds(new Rectangle(MUSIC_TOGGLE_BUTTON_LOC, new Dimension(125, 60)));
		muteSound.setContentAreaFilled(false);
		muteMusic.setContentAreaFilled(false);
		muteSound.setBorderPainted(false);
		muteMusic.setBorderPainted(false);
		muteSound.setBorder(null);
		muteMusic.setBorder(null);
		muteSound.setFocusable(false);
		muteMusic.setFocusable(false);
		
		musicVolume = new JSlider(JSlider.HORIZONTAL, VOLUME_MIN, VOLUME_MAX, VOLUME_MAX / 2);
		soundVolume = new JSlider(JSlider.HORIZONTAL, VOLUME_MIN, VOLUME_MAX, VOLUME_MAX / 2);
		
		musicVolume.setBounds(new Rectangle(MUSIC_SLIDER_LOC, new Dimension(300, 20)));
		soundVolume.setBounds(new Rectangle(SOUND_SLIDER_LOC, new Dimension(300, 20)));
		
		musicVolume.setValue(VOLUME_MAX);
		soundVolume.setValue(VOLUME_MAX);
		musicVolume.setFocusable(false);
		soundVolume.setFocusable(false);
		musicVolume.setBorder(null);
		soundVolume.setBorder(null);
		musicVolume.setOpaque(false);
		soundVolume.setOpaque(false);
		
		musicVolume.setUI(new CustomSlider(musicVolume));
		soundVolume.setUI(new CustomSlider(soundVolume));
		
		SettingsListener listener = new SettingsListener();
		muteSound.addChangeListener(listener);
		muteMusic.addChangeListener(listener);
		musicVolume.addChangeListener(listener);
		soundVolume.addChangeListener(listener);
		
		KeyChangeListener keyListener = new KeyChangeListener();
		addKeyListener(keyListener);
		
		add(musicLabel);
		add(soundLabel);
		add(muteSound);
		add(muteMusic);
		add(musicVolume);
		add(soundVolume);
		add(settingsLabel);
		add(controlsLabel);
		add(rightKeyLabel);
		add(leftKeyLabel);
		add(shootKeyLabel);
	}
	
	/**
	 * Updates the setting of {@code oldKey} which is either
	 * <ul>
	 * <li> {@code leftKey} </li>
	 * <li> {@code rightKey} </li>
	 * <li> {@code shootKey} </li>
	 * </ul>
	 * with {@code newKey}
	 * 
	 * @param oldKey the key to be replaced.
	 * @param newKey new assignment for {@code oldKey}
	 */
	public void updateKey(int oldKey, int newKey)
	{
		if(oldKey == rightKey)
		{
			rightKey = newKey;
		}
		else if(oldKey == leftKey)
		{
			leftKey = newKey;
		}
		else if(oldKey == shootKey)
		{
			shootKey = newKey;
		}
	}
	
	/**
	 * Returns the right motion key of the spaceship to the user.
	 * 
	 * @return the right motion key
	 */
	public int getRightKey()
	{
		return rightKey; 
	}
	
	/**
	 * Returns the left motion key of the spaceship to the user.
	 * 
	 * @return the left motion key
	 */
	public int getLeftKey()
	{
		return leftKey;
	}
	
	/**
	 * Returns the shoot key of the spaceship to the user.
	 * 
	 * @return the shoot key
	 */
	public int getShootKey()
	{
		return shootKey;
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 */
	private class CustomSlider extends BasicSliderUI
	{

		 private BasicStroke stroke = new BasicStroke(5f, BasicStroke.CAP_ROUND, 
		            BasicStroke.JOIN_ROUND, 0f, new float[] {15f}, 1f);

		    public CustomSlider(JSlider b) {
		        super(b);
		    }

		    @Override
		    public void paint(Graphics g, JComponent c) {
		    	
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		                RenderingHints.VALUE_ANTIALIAS_ON);
		        super.paint(g, c);
		    }

		    @Override
		    protected Dimension getThumbSize() {
		        return new Dimension(12, 16);
		    }

		    @Override
		    public void paintTrack(Graphics g) {
		    	
		        Graphics2D gra = (Graphics2D) g;
		        
		        Stroke reset = gra.getStroke();
		        gra.setStroke(stroke);
		        gra.setPaint(Color.white);
		        
		        if(slider.getOrientation() == SwingConstants.HORIZONTAL) 
		        	gra.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, 
		                    trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
		        else
		        	gra.drawLine(trackRect.x + trackRect.width / 2, trackRect.y, 
		                    trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
		        gra.setStroke(reset);
		    }

		    @Override
		    public void paintThumb(Graphics g) {
		    	
		        Graphics2D gra = (Graphics2D) g;
		        GradientPaint gp = new GradientPaint((float) thumbRect.x, (float) (thumbRect.y + thumbRect.height / 2), Color.decode("#271EE5"), 
		        		(float) (thumbRect.x + thumbRect.width + 20), (float) (thumbRect.y + thumbRect.height / 2), Color.decode("#E3E3E3")); 
		        
		        Stroke reset = gra.getStroke();
		        gra.setStroke(stroke);
		        gra.setPaint(gp);
		        gra.fillOval(thumbRect.x, thumbRect.y, thumbRect.width + 5, thumbRect.width + 5);
		        gra.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, 
	                    trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
		        gra.setStroke(reset);
		        repaint();
		    }
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 */
	private class SettingsListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			Object source = e.getSource();
			if(source == muteSound)
			{
				boolean state = muteSound.isSelected();
				if(state)
					muteSound.setIcon(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "ON" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
				else
					muteSound.setIcon(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "OFF" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
				controller.setSoundVolume(state);
			}
			else if(source == muteMusic)
			{
				boolean state = muteMusic.isSelected();
				if(state)
					muteMusic.setIcon(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "ON" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
				else
					muteMusic.setIcon(new ImageIcon(FileManager.ImageUtils.TOGGLE_BUTTON_PATH + "OFF" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
				controller.setMusicVolume(state);
			}
			else if(source == soundVolume)
				controller.setSoundVolume(soundVolume.getValue() / 100.0f);
			else if(source == musicVolume)
				controller.setMusicVolume(musicVolume.getValue() / 100.0f);
		}
	}
	
	private class KeyChangeListener extends KeyAdapter
	{
		private int previousKey = 0;
		private boolean isInSetState = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_ESCAPE)
			{
				if(pointer != null)
					pointer = null;
				else
					controller.goBackWhenESC();
				controller.playButtonSound();
			}
			if(key == KeyEvent.VK_ENTER)
			{
				controller.playButtonSound();
				if(isInSetState)
				{
					if(pointer == null)
						return;
					String text = pointer.getMessage().substring(0, pointer.getMessage().indexOf(":") + 1) + " " +KeyEvent.getKeyText(previousKey);
					pointer.setMessage(text);
					isInSetState = false;
					
					if(pointer == leftKeyLabel)
						leftKey = previousKey;
					else if(pointer == rightKeyLabel)
						rightKey = previousKey;
					else if(pointer == shootKeyLabel)
						shootKey = previousKey;
				}
				else 
					isInSetState = true;
			}
			else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT)
			{
				controller.playButtonSound();
				if(pointer == null)
				{
					if(key == KeyEvent.VK_RIGHT)
						pointer = leftKeyLabel;
					else if(key == KeyEvent.VK_LEFT)
						pointer = rightKeyLabel;
				}
				else if(key == KeyEvent.VK_LEFT)
				{
					if(pointer == leftKeyLabel)
						pointer = rightKeyLabel;
					else if(pointer == shootKeyLabel)
						pointer = leftKeyLabel;
					else 
						pointer = shootKeyLabel;
				}
				else if(key == KeyEvent.VK_RIGHT)
				{
					if(pointer == leftKeyLabel)
						pointer = shootKeyLabel;
					else if(pointer == shootKeyLabel)
						pointer = rightKeyLabel;
					else 
						pointer = leftKeyLabel;
				}
				pointer.repaint();
			}
			previousKey = key;
		}
	}
}
