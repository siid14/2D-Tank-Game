package tankrotationexample.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip sound;

    // constructor to initialize a Sound object with a given Clip
    public Sound(Clip sound) {
        this.sound = sound;
    }

    // play the sound clip
    public void playSound(){
        // show where i am in the clip
        this.sound.setFramePosition(0); // reset the playback position to the beginning
        this.sound.start();  // start playing the sound
    }

    // set the sound clip to loop indefinitely
    public void setLooping(){
        // loop sound indefinitely
        this.sound.loop(Clip.LOOP_CONTINUOUSLY); // set the loop count to loop indefinitely
    }

    // stop playing the sound clip
    public void stop(){
        if (this.sound.isRunning()) {
            this.sound.stop(); // stop the sound playback
        }
    }

    // Set the volume level of the sound clip
    public void setVolume(float level){
    FloatControl volume = (FloatControl) this.sound.getControl(FloatControl.Type.MASTER_GAIN);
    volume.setValue(20.0f * (float)Math.log10(level)); // convert volume level to dB scale
    }
}
