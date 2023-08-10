package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    float x, y; // position of the animation
    private List<BufferedImage> frames; // list of frames to be displayed in the animation
    private long timeSinceUpdate = 0;   // time tracking variables for animation update
    private long delay = 50; // delay between frame updates
    private int currentFrame = 0; // current frame being displayed

    private boolean isRunning = false; // flag to indicate if the animation is currently running

    // constructor for the animation class
    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        isRunning = true; // animation starts running upon creation
    }

    // update the animation's state
    public void update(){
        // check if it's time to update the frame
        if (timeSinceUpdate + delay < System.currentTimeMillis()) {
            this.timeSinceUpdate = System.currentTimeMillis();
            this.currentFrame++;

            // check if the animation has reached its end
            if(this.currentFrame == this.frames.size()){
                isRunning = false; // stop the animation
            }
        }
    }

    // draw the current frame of the animation
    public void drawImage(Graphics2D g2d){
        if(isRunning){
            // draw the current frame at the specified position
            g2d.drawImage(this.frames.get(currentFrame), (int)x, (int)y, null);
        };
    }
}
