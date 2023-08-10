package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject implements PowerUp {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    boolean hasCollided = false; // flag to track if the power-up has been collected
    private int life = 1;
    GameWorld gw;

    // constructor for creating a Health power-up
    public Health(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    // get the hitbox of the Health power-up
    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    // handle collision with other game objects
    @Override
    public void collides(GameObject obj) {
        if (!hasCollided && obj instanceof Tank) {
            applyPowerUp((Tank) obj); // apply power-up effect to the Tank

            // gw.anims.add(new Animation(x , y, ResourceManager.getAnimation("bulletshoot"))); // ! don't work
        }

    }

    // check if the Health power-up has been collected (always returns false)
    @Override
    public boolean hasCollided() {
        return false;
    }

    // draw the Health power-up on the screen if it hasn't been collected
    public void drawImage(Graphics buffer) {
        if(!this.hasCollided) {
            buffer.drawImage(this.img, (int)x, (int)y, null);
        }
    }

    // apply the power-up effect to the Tank
    @Override
    public void applyPowerUp(Tank tank) {
        tank.increaseLife(100); // increase tank's life by 100
        hasCollided = true; // mark the power-up as collected
    }
}
