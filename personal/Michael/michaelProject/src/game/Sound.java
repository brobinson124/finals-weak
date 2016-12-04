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

    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;

//    /**
//     * @param filename the name of the file that is going to be played
//     */
//    public void playSound(String filename){
//
//        String strFilename = filename;
//
//        try {
//            soundFile = new File(strFilename);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        try {
//            audioStream = AudioSystem.getAudioInputStream(soundFile);
//        } catch (Exception e){
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        audioFormat = audioStream.getFormat();
//
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//        try {
//            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
//            sourceLine.open(audioFormat);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//            System.exit(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        sourceLine.start();
//
//        int nBytesRead = 0;
//        byte[] abData = new byte[BUFFER_SIZE];
//        while (nBytesRead != -1) {
//            try {
//                nBytesRead = audioStream.read(abData, 0, abData.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (nBytesRead >= 0) {
//                @SuppressWarnings("unused")
//                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
//            }
//        }
//
//        sourceLine.drain();
//        sourceLine.close();
//    }
    
    /**
     * Handles playing, stoping, and looping of sounds for the game.
     * @author Tyler Thomas
     *
     */
   // public class Sound {
        private Clip clip;
        public void playSound(String fileName) {
            // specify the sound to play
            // (assuming the sound can be played by the audio system)
            // from a wave File
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
        public void play(){
            clip.setFramePosition(0);  // Must always rewind!
            clip.start();
        }
        public void loop(){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop(){
                clip.stop();
            }
      //  }
}