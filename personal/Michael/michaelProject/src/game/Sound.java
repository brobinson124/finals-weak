package game;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    /**
     * Handles playing, stoping, and looping of sounds for the game.
     * @author Tyler Thomas
     *
     */
   // public class Sound {
        private Clip clip;
        public void playSound(String fileName) {
            // specify the sound to play, must be a ".wav" file!
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                 // load the sound into memory (a Clip)
                    clip = AudioSystem.getClip();
                    clip.open(sound);
                }
                else {
                    throw new RuntimeException("Sound: file not found: " + fileName);
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Sound: Malformed URL: " + e);
            }
            catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
                throw new RuntimeException("Sound: Unsupported Audio File: " + e);
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Sound: Input/Output Error: " + e);
            }
            catch (LineUnavailableException e) {
                e.printStackTrace();
                throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
            }

        // play, stop, loop the sound clip
        }
        public boolean play(){
            clip.setFramePosition(0);  // Must always rewind!
            clip.start();
            return true;
        }
        public void loop(){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop(){
                clip.stop();
            }
}