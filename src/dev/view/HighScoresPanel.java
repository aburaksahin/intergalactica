package dev.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dev.control.FileManager;

/**
 * HighScoresPanel
 *
 * Description: This panel displays the highscores made by user(s). Only 10 of the
 * top scores are displayed.
 *
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public class HighScoresPanel extends BasicPanel {

	/* Constants */
	private final Point INITIAL_ENTRY_LOC = new Point(230, 130); 
	private final Point TITLE_LOC = new Point(60, 20);
	private final Point SCORES_LABEL_LOC = new Point(550, 70);
	private final Point RESET_BUTTON_LOC = new Point(500, 500);
	private final Point NAMES_LABEL_LOC = new Point(250, 70);
	private final int SCORE_NAME_OFFSET = 360;
	
	/* Variables */
	private Widget[] names;
	private Widget[] scores;
	private Widget title;
	private Widget namesLabel;
	private Widget scoresLabel;
	private Widget resetButton;
	
	/**
	 * Constructor for Highscores panel.
	 * 
	 * @param background background image.
	 * @param sc screencontroller interface.
	 */
	public HighScoresPanel(BufferedImage background, ScreenController sc) {
		super(background, sc);
		
		try 
		{
			title = new Widget("- Highscores -", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)),
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) TITLE_LOC.getX(), (int) TITLE_LOC.getY(), true);
			namesLabel = new Widget("Names", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)),
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) NAMES_LABEL_LOC.getX(), (int) NAMES_LABEL_LOC.getY(), true);

			scoresLabel = new Widget("Scores", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)),
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) SCORES_LABEL_LOC.getX(), (int) SCORES_LABEL_LOC.getY(), true);
			
			resetButton = new Widget("Reset", ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)),
					FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) RESET_BUTTON_LOC.getX(), (int) RESET_BUTTON_LOC.getY(), true){
				
				
				@Override
				public void mousePressed(MouseEvent e) {
					controller.playButtonSound();
					controller.resetHighscores();
					updateTable();
				}
				
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					Graphics2D gra = (Graphics2D) g;
					
					Stroke defaultStroke = gra.getStroke();
					gra.setColor(getBackground());
					gra.setStroke(new BasicStroke(7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
					gra.drawRect(0, 0, getWidth(), getHeight());
					gra.setStroke(defaultStroke);
				}
			};
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[HighScoresPanel] Failed to load widget images.");
		}
		setEntries();
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					controller.playButtonSound();
					controller.goBackWhenESC();
			}
		});
		
		title.resizeFont(26f);
		namesLabel.resizeFont(26f);
		scoresLabel.resizeFont(26f);
		resetButton.resizeFont(26f);
		
		title.setFontColor(Color.WHITE);
		namesLabel.setFontColor(Color.WHITE);
		scoresLabel.setFontColor(Color.WHITE);
		resetButton.setFontColor(Color.WHITE);
		
		add(title);
		add(namesLabel);
		add(scoresLabel);
		add(resetButton);
	}
	
	
	private void setEntries()
	{
		String[] highscores = controller.getHighscorers();
		
		if(highscores == null)
			controller.resetHighscores();
		highscores = controller.getHighscorers();
		
		names = new Widget[FileManager.NUMBER_OF_ENTRIES / 2];
		scores = new Widget[FileManager.NUMBER_OF_ENTRIES / 2];
		
		int count = 0;
		for(int i = 0; i < highscores.length / 2; i++)
		{
			try 
			{
				names[i] = new Widget("" + (i + 1) + ". " + highscores[count], ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)), 
						FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) INITIAL_ENTRY_LOC.getX(), (int) (INITIAL_ENTRY_LOC.getY() + FONT_SIZE * i), true);
				scores[i] = new Widget(highscores[count + 1], ImageIO.read(new File(FileManager.ImageUtils.MENU_BACKGROUND_PATH)), 
						FileManager.ImageUtils.MENU_BACKGROUND_PATH, (int) INITIAL_ENTRY_LOC.getX() + SCORE_NAME_OFFSET, 
						(int) (INITIAL_ENTRY_LOC.getY() + FONT_SIZE * i), true);
				count += 2;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.err.println("[ERROR]:[HighScoresPanel] Failed to load the image for entry no. " + (i + 1));
			}
			names[i].setFontColor(Color.WHITE);
			scores[i].setFontColor(Color.WHITE);
			add(names[i]);
			add(scores[i]);
		}
	}
	
	private void updateTable()
	{
		String[] highscores = controller.getHighscorers();

		if(highscores == null)
			controller.resetHighscores();
		highscores = controller.getHighscorers();
		
		int count = 0;
		for(int i = 0; i < highscores.length / 2; i++)
		{
			names[i].setMessage("" + (i + 1) + ". " + highscores[count]);
			scores[i].setMessage(highscores[count + 1]);
			names[i].adjust();
			scores[i].adjust();
			
			count += 2;
		}
	}
}
