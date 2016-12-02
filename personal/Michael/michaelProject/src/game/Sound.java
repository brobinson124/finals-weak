package game;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;
 
public class Sound {
    /** The ogg sound effect */
    private Audio oggEffect;
    /** The wav sound effect */
    //private Audio wavEffect;
    /** The aif source effect */
    private Audio aifEffect;
    /** The ogg stream thats been loaded */
    private Audio oggStream;
    /** The mod stream thats been loaded */
    private Audio modStream;
     

    /**
    * Initialise resources
    */
    public void sound() {
 
        try {
        // you can play oggs by loading the complete thing into 
        // a sound
        //oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("testdata/restart.ogg"));
             
        // or setting up a stream to read from. Note that the argument becomes
        // a URL here so it can be reopened when the stream is complete. Probably
        // should have reset the stream by thats not how the original stuff worked
        //oggStream = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("testdata/bongos.ogg"));
             
        // can load mods (XM, MOD) using ibxm which is then played through OpenAL. MODs
        // are always streamed based on the way IBXM works
        //modStream = AudioLoader.getStreamingAudio("MOD", ResourceLoader.getResource("testdata/SMB-X.XM"));
 
        // playing as music uses that reserved source to play the sound. The first
        // two arguments are pitch and gain, the boolean is whether to loop the content
       // modStream.playAsMusic(1.0f, 1.0f, true);
             
        // you can play aifs by loading the complete thing into 
        // a sound
        //aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("testdata/burp.aif"));
 
        // you can play wavs by loading the complete thing into 
        // a sound
        Audio wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/.wav"));
        } catch (IOException e) {
        e.printStackTrace();
    }
    }
     
}
