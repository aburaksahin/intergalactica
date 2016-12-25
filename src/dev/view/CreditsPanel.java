package dev.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dev.control.FileManager;

/**
 * CreditsPanel
 *
 * Description: This panel shows the team that built 
 * this software
 *
 * @author A. Burak Sahin
 * @date   Dec 19, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class CreditsPanel extends BasicPanel {

	private final Point TITLE_LOC = new Point(290, 50);
	private final Point CREDITS_LOC = new Point(230, 125);
	
	private Widget title;
	private Widget credits;
	
	/**
	 * Constructor
	 * 
	 * @param background
	 * @param sc
	 */
	public CreditsPanel(BufferedImage background, ScreenController sc) {
		super(background, sc);
		
		try {
			title = new Widget("Credits - Group 18", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH )), 
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) TITLE_LOC.getX(), (int) TITLE_LOC.getY(), true);
			credits = new Widget(ImageIO.read(new File(FileManager.ImageUtils.CREDITS_IMAGE_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION)), 
					FileManager.ImageUtils.CREDITS_IMAGE_PATH + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION, (int) CREDITS_LOC.getX(), 
					(int) CREDITS_LOC.getY(), false);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[CreditsPanel] Error while loading images.");
		}
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					controller.playButtonSound();
					controller.goBackWhenESC();
				}
			}
		});
		
		title.setFontColor(Color.WHITE);
		title.resizeFont(30f);
		
		add(title);
		add(credits);
	}

}
