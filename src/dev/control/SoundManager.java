package dev.control;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dev.view.SettingsPanel;

/**
 * SoundManager
 *
 * Description: 
 *
 * @author A. Burak Sahin
 * @date   Dec 19, 2016
 * @version v1.0
 */
public final class SoundManager {

	/** Variables */
	private static SoundManager instance = new SoundManager();
	
	private boolean backgroundEnabled;
	private boolean sampleEnabled;
	private float sounddB;
	private float musicdB;
	private FloatControl soundControl;
	private FloatControl musicControl;
	
	/**
	 * Don't let anyone instantiate this class.
	 */
	 private SoundManager() 
	 {
		 backgroundEnabled = true;
		 sampleEnabled = true;
		 sounddB = 1f;
		 musicdB = 1f;
	 }
	 
	 /**
	  * Returns the mere instance of this class.
	  * 
	  * @return only {@code instance} of this class.
	  */
	 public static SoundManager getInstance()
	 {
		 return instance;
	 }
	 
	 /**
	  * Mutes or Unmutes the sample sounds according to the value {@code set}. 
	  * 
	  * @param set value that determines muteness.
	  */
	 public void setSound(boolean set)
	 {
		 sampleEnabled = set;
	 }
	 
	 /**
	  * Mutes or Unmutes the background music tracks according to the value {@code set}. 
	  * 
	  * @param set value that determines muteness.
	  */
	 public void setMusic(boolean set)
	 {
		 backgroundEnabled = set;
		 setMusicVolume(set ? 1f : 0f);
	 }
	 
	 /**
	  * Sets a value of {@code backgroundMusic} according to the position/percentage of the <em>Music</em> slider in 
	  * {@link SettingsPanel} with a number between:
	  * <p>[0, 1] = [<b>MIN_VOLUME</b>, <b>MAX_VOLUME</b>]</p>
	  * 
	  * @param volume value to be set.
	  */
	 public void setMusicVolume(float volume)
	 {
		 if(volume > 1 || volume < 0)
			 return;
		  musicdB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
		  if(musicControl != null)
			  musicControl.setValue(musicdB);
	 }
	 
	 /**
	  * Sets a value of {@code sample} according to the position/percentage of the <em>Sound</em> slider in 
	  * {@link SettingsPanel} with a number between:
	  * <p>[0, 1] = [<b>MIN_VOLUME</b>, <b>MAX_VOLUME</b>]</p>
	  * 
	  * @param volume value to be set.
	  */
	 public void setSoundVolume(float volume)
	 {
		 if(volume > 1 || volume < 0)
			 return;
		 sounddB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
		 if(soundControl != null)
			 soundControl.setValue(sounddB);
	 }
	 
	 /**
	  * Plays the background music. If {@code isInMenu} is <b>{@code true}</b>, then
	  * only the <em>Theme</em> song is played. 
	  * <p> 
	  * Otherwise:
	  * </p>
	  * <ul>
	  * <li><em>Fury</em></li>
	  * <li><em>Bass City</em></li>
	  * <li><em>Atmospherik Mekanisms</em></li>
	  * </ul>
	  * 
	  * <p>are played.</p>
	  * 
	  * <p><b><em>
	  * 
	  * All tracks excluding
	  * 
	  * @param name
	  * @param isInMenu
	  */
	 public void playBackground(String name)
	 {
		 Clip clip = null;
		 if(!backgroundEnabled)
			  return;
		 try
		 {
			 clip = AudioSystem.getClip();
			 AudioInputStream adjustedInputStream = AudioSystem.getAudioInputStream(clip.getFormat(),
					 AudioSystem.getAudioInputStream(new File(name)));
			 clip.open(adjustedInputStream);
			 
		 }
		 catch(IOException | LineUnavailableException | UnsupportedAudioFileException e)
		 {
			 e.printStackTrace();
			 System.err.println("[ERROR]:[SoundManager] Error while playing the song.");
		 }
			
			 clip.addLineListener(new ClipListener());
			 clip.loop(Clip.LOOP_CONTINUOUSLY);
			 clip.start();
			 musicControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			 musicControl.setValue(musicdB);
			
	 }
	 
	 public void playSound(String name)
	 {
		 if(!sampleEnabled)
			 return;
		 try
		 {
			 Clip clip = AudioSystem.getClip();
			 AudioInputStream adjustedInputStream = AudioSystem.getAudioInputStream(clip.getFormat(),
					 AudioSystem.getAudioInputStream(new File(name)));
			 clip.open(adjustedInputStream);
			 clip.addLineListener(new ClipListener());
			 soundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			 soundControl.setValue(sounddB);
			 clip.start();
		 }
		 catch(IOException | LineUnavailableException | UnsupportedAudioFileException e)
		 {
			 e.printStackTrace();
			 System.err.println("[ERROR]:[SoundManager] Error while playing the song.");
		 }
	 }
	 
	 private class ClipListener implements LineListener
	 {

		@Override
		public void update(LineEvent event) {
			if(event.getType().equals(LineEvent.Type.STOP))
			{
				Line sound = (Line) event.getLine();
				sound.close();
			}
		}
	 }
} 
