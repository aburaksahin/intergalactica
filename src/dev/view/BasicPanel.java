package dev.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dev.control.FileManager;


/**
 * BasicPanel
 *
 * Description:
 *
 * @author A. Burak Sahin
 * @date   Dec 15, 2016
 * @version v1.0
 */
@SuppressWarnings("serial")
public abstract class BasicPanel extends JPanel { 
	
	public static final Rectangle PANEL_DIMENSION;
	private static final Font FONT_CONSTANTINE;
	private static final Color BACKGROUND_COLOR;
	protected static final float FONT_SIZE = 24f;
	private static final float FONT_MARGIN_FACTOR = 1.2f;
	private static final float FONT_WIDTH_NORMALIZE_CONSTANT = 0.6f;
	
	protected BufferedImage background;
	protected JPanel topBar;
	
	protected ScreenController controller; 
	
	static {
		BACKGROUND_COLOR = Color.decode("#EDEDED");
		PANEL_DIMENSION = new Rectangle(0, 0, 975, 620);
		
		Font temp = null;
		try 
		{
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, 
					new File(FileManager.ImageUtils.DEFAULT_WIDGET_FONT_PATH)));
			temp = Font.createFont(Font.TRUETYPE_FONT, new File(FileManager.ImageUtils.DEFAULT_WIDGET_FONT_PATH));
		} 
		catch (FontFormatException | IOException e) 
		{
			e.printStackTrace();
			System.err.println("[ERROR]:[BasicPanel] Unable to get the font from " + FileManager.ImageUtils.DEFAULT_WIDGET_FONT_PATH);
			System.exit(1);
		}
		FONT_CONSTANTINE = temp.deriveFont(FONT_SIZE);
	}
	
	public BasicPanel(BufferedImage background, ScreenController sc)
	{
		this.background = background;
		controller = sc;
		
		topBar = new JPanel();
		topBar.setOpaque(false);
		topBar.setLayout(null);
		topBar.setBounds((int) PANEL_DIMENSION.getX(), (int) PANEL_DIMENSION.getY(), (int) PANEL_DIMENSION.getWidth(), (int) FONT_SIZE);
		
		TopBarDragListener dragListener = new TopBarDragListener();
		topBar.addMouseListener(dragListener); 
		topBar.addMouseMotionListener(dragListener);
		
		Widget closeWidget = new Widget("X", Color.decode("#E81123"));
		Widget iconifyWidget = new Widget("_", Color.decode("#ADADAD"));
		
		closeWidget.setOpaque(false);
		closeWidget.setFontColor(Color.WHITE);
		iconifyWidget.setOpaque(false);
		
		MouseListener listener = new TopBarWidgetListener();
		
		closeWidget.addMouseListener(listener);
		iconifyWidget.addMouseListener(listener);
		
		closeWidget.setToolTipText("Close");
		iconifyWidget.setToolTipText("Minimize");
		
		closeWidget.setLocation(new Point(topBar.getX() + topBar.getWidth() - closeWidget.getWidth(), topBar.getY()));
		iconifyWidget.setLocation(topBar.getX() + topBar.getWidth() - closeWidget.getWidth() - iconifyWidget.getWidth(), topBar.getY());
		
		topBar.add(closeWidget);
		topBar.add(iconifyWidget);
		add(topBar);
		
		setFocusable(true);
		setLayout(null);
		setPreferredSize(PANEL_DIMENSION.getSize());
		setBounds((int) PANEL_DIMENSION.getX(), (int) PANEL_DIMENSION.getY(), (int) PANEL_DIMENSION.getWidth(), (int) PANEL_DIMENSION.getHeight());
		setBackground(BACKGROUND_COLOR);
	}
	
	/**
	 * 
	 * If we don't want to insert any background image
	 * 
	 * @param sc
	 */
	public BasicPanel(ScreenController sc)
	{
		this(null, sc);
	}
	
	/**
	 * 
	 * @param newBG
	 */
	public void updateBackground(BufferedImage newBG)
	{
		background = newBG;
		repaint();
	}
	
	/**
	 * Always remain in HD mode. 
	 * <p><b>Warning:</b> Revise if performs badly.</p>
	 * 
	 * @param g {@code Graphics} object to paint on.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D gra = (Graphics2D) g;
		
		//Allow antialiasing and rendering for High-Definition.
		gra.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gra.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gra.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		gra.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gra.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		if(background != null)
			gra.drawImage(background, getX(), getY(), this);
			
	}
	
	/**
	 * 
	 * 
	 *
	 */
	protected class Widget extends JPanel implements MouseListener
	{	
		private static final String NULL_MESSAGE = "[NULL]";
		
		private String message; 
		private Font currentFont;
		private Color fontColor;
		private float opacity;
		private BufferedImage bgImage;
		private String imagePath;
		private boolean crop;
		
		/**
		 * 
		 */
		protected Widget()
		{
			this(NULL_MESSAGE, BasicPanel.this.getX(), BasicPanel.this.getY(), BACKGROUND_COLOR, -1f, null, null, false);
		}
		
		/**
		 * 
		 * @param msg
		 */
		protected Widget(String msg, Color bgColor)
		{
			this(msg, BasicPanel.this.getX(), BasicPanel.this.getY(), bgColor, -1f, null, null, false);
		}
		
		/**
		 * 
		 * @param msg
		 */
		protected Widget(String msg)
		{
			this(msg, BasicPanel.this.getX(), BasicPanel.this.getY(), BACKGROUND_COLOR, -1f, null, null, false);
		}
		
		/**
		 * 
		 * @param backgroundImage
		 */
		protected Widget(BufferedImage backgroundImage, String imagePath)
		{
			this(NULL_MESSAGE, BasicPanel.this.getX(), BasicPanel.this.getY(), BACKGROUND_COLOR, -1f, backgroundImage, imagePath, false);
		}
		
		/**
		 * 
		 * @param backgroundImage
		 * @param x
		 * @param y
		 */
		protected Widget(BufferedImage backgroundImage, String imagePath, int x, int y)
		{
			this(NULL_MESSAGE, x, y, BACKGROUND_COLOR, -1f, backgroundImage, imagePath, false);
		}
		
		/**
		 * 
		 * @param backgroundImage
		 * @param imagePath
		 * @param x
		 * @param y
		 * @param crop
		 */
		protected Widget(BufferedImage backgroundImage, String imagePath, int x, int y, boolean crop)
		{
			this(NULL_MESSAGE, x, y, BACKGROUND_COLOR, -1f, backgroundImage, imagePath, false);
		}
		
		/**
		 * 
		 * @param msg
		 * @param backgroundImage
		 * @param imagePath
		 * @param x
		 * @param y
		 */
		protected Widget(String msg, BufferedImage backgroundImage, String imagePath, int x, int y, boolean crop)
		{
			this(msg, x, y, BACKGROUND_COLOR, -1f, backgroundImage, imagePath, crop);
		}
		
		/**
		 * 
		 * @param msg
		 * @param x
		 * @param y
		 */
		protected Widget(String msg, int x ,int y)
		{
			this(msg, x, y, BACKGROUND_COLOR, -1f, null, null, false);
		}
		
		/**
		 * 
		 * @param msg
		 * @param x
		 * @param y
		 * @param bgColor
		 */
		protected Widget(String msg, int x, int y, Color bgColor, float opacity, BufferedImage bgImage, String imagePath, boolean crop) 
		{
			message = msg;
			this.imagePath = imagePath;
			currentFont = FONT_CONSTANTINE;
			this.opacity = opacity;
			this.bgImage = bgImage;
			fontColor = Color.decode("#333333");
			this.crop = crop;
			
			int width;
			int height;
			
			if (crop || this.bgImage == null)
			{
				if(message.equalsIgnoreCase("X") || message.equals("_"))
					width = (int) Math.round(message.length() * currentFont.getSize() * FONT_MARGIN_FACTOR);
				else
					width = (int) Math.round(message.length() * FONT_WIDTH_NORMALIZE_CONSTANT * currentFont.getSize() * FONT_MARGIN_FACTOR);
				height = (int) Math.round(currentFont.getSize() * FONT_MARGIN_FACTOR);
			}
			else
			{
				width = bgImage.getWidth();
				height = bgImage.getHeight();
			}
			
			setLayout(null);
			setOpaque(false);
			setBounds(x, y, width, height);
			setBackground(bgColor);
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
		public String getMessage()
		{
			return message;
		}
		
		public void setFontColor(Color newFColor)
		{
			fontColor = newFColor;
			repaint();
		}
		
		public void resizeFont(float size)
		{
			currentFont = currentFont.deriveFont(size);
			adjust();
		}
		
		public void adjust()
		{
			int width = (int) Math.round(message.length() * FONT_WIDTH_NORMALIZE_CONSTANT * currentFont.getSize() * FONT_MARGIN_FACTOR);
			int height = (int) Math.round(currentFont.getSize() * FONT_MARGIN_FACTOR);
			setBounds(getX(), getY(), width, height);
			repaint();
		}
		
		public String getImagePath()
		{
			return imagePath;
		}

		public void setSelected(boolean selected)
		{
			try
			{
				if(selected)
					bgImage = ImageIO.read(new File(imagePath + "_hover" + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
				else
					bgImage = ImageIO.read(new File(imagePath + FileManager.ImageUtils.DEFAULT_IMAGE_EXTENSION));
			}
			catch(IOException e)
			{
				System.err.println("[ERROR]:[BasicPanel.Widget] Failed to load the image while (de)selecting.");
				e.printStackTrace();
			}
			repaint();
		}
		
		public void setOpacity(float newOpacity)
		{
			opacity = newOpacity;
			repaint();
		}
		
		public float getOpacity()
		{
			return opacity;
		}
		
		public void setImage(BufferedImage img)
		{
			bgImage = img;
			repaint();
		}
		
		public void setMessage(String msg)
		{
			message = msg;
			adjust();
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			Graphics2D gra = (Graphics2D) g;
			
			//Allow antialiasing and rendering for High-Definition.
			gra.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			gra.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gra.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			gra.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			gra.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			
			Rectangle2D.Float bounds = (Float) new TextLayout(message, currentFont, gra.getFontRenderContext()).getBounds();
			
			if(crop)
			{
				gra.drawImage(bgImage, 0, 0, getWidth(), getHeight(), getX(), getY(), getX() + getWidth(), getY() + getHeight(), null);
				
				gra.setFont(currentFont);
				gra.setColor(fontColor);
				gra.drawString(message, getWidth() / 2 - bounds.width / 2, getHeight() / 2 + bounds.height / 2);
			}
			else if (bgImage == null)
			{
				Composite c;
				if (!isOpaque())
					c = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, .0f);
				else if (opacity != -1)
					c = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity);
				else
					c = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f);
				
				gra.setComposite(c);
				gra.setFont(currentFont);
				gra.setColor(fontColor); 
				
				gra.drawString(message, getWidth() / 2 - bounds.width / 2, getHeight() / 2 + bounds.height / 2);
			}
			else
			{
				if(opacity != -1 && opacity > 0 && opacity < 1)
				{
					gra.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
					gra.drawImage(bgImage, 0, 0, null);
					gra.dispose();
				}
				else
					gra.drawImage(bgImage, 0, 0, null);
			}
		}
	}
	
	/**
	 * 
	 * @author A. Burak Sahin
	 *
	 */
	private class TopBarWidgetListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) {
			
			Widget thisWidget = (Widget) e.getSource();
			
			String thisMsg = thisWidget.getMessage();
			if (thisMsg.equalsIgnoreCase("X"))
			{
				thisWidget.setBackground(Color.decode("#E81123"));
				controller.signalShutdown();
			}
			else if(thisMsg.equals("_"))
			{
				thisWidget.setBackground(BACKGROUND_COLOR);
				controller.signalIconify();
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e)
		{
			Widget thisWidget = (Widget) e.getSource();
			thisWidget.setOpaque(true);
			thisWidget.repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent e)
		{
			Widget thisWidget = (Widget) e.getSource();
			thisWidget.setOpaque(false);
			thisWidget.repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			Widget thisWidget = (Widget) e.getSource();
			if(!thisWidget.getMessage().equals("_"))
				thisWidget.setBackground(Color.decode("#C60C1B"));
			else
				thisWidget.setBackground(Color.decode("#D3D3D3"));
		}
	}
	
	private class TopBarDragListener extends MouseAdapter
	{
		int x = 0;
		int y = 0;
		
		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getXOnScreen();
			y = e.getYOnScreen();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Point frameLoc = controller.getFrameLocation();
			int thisX = e.getXOnScreen();
			int thisY = e.getYOnScreen();
			
			controller.setFrameLocation((int) (frameLoc.getX() + (thisX - x)), (int) (frameLoc.getY() + (thisY - y)));
			x = thisX;
			y = thisY;
		}
	}
}
