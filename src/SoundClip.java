import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class SoundClip {
	private URL url;
	private Clip clip;
	private String filename;
	private int identifier;
   
	public SoundClip(String filename, int identifier) {
		this.filename = filename;
		this.identifier = identifier;
	}

	public void stop(){
		clip.stop();
	}
   
	public void start(){
		try{
			url = this.getClass().getClassLoader().getResource(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			if(identifier == 0)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		
			if(identifier == 1)
				clip.start();
		}catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			e.printStackTrace();
		}
   }
}
