package tankrotationexample.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip sound;

    public Sound(Clip sound) {
        this.sound = sound;
    }

    public void playSound(){
        // show where i am in the clip
        this.sound.setFramePosition(0); // start from beginning 0
        this.sound.start();
    }

    public void setLooping(){
        // loop sound indefinitely
        this.sound.loop(Clip.LOOP_CONTINUOUSLY); // set the loop count to -1
    }

    public void stop(){
        if (this.sound.isRunning()) {
            this.sound.stop();
        }
    }

    public void setVolume(float level){

    FloatControl volume = (FloatControl) this.sound.getControl(FloatControl.Type.MASTER_GAIN);
    volume.setValue(20.0f * (float)Math.log10(level));
    }
}
