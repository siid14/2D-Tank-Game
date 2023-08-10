package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shield extends GameObject implements PowerUp {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    boolean hasCollided = false; // flag to track if the power-up has been collected

    // constructor for creating a Shield power-up
    public Shield(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    // get the hitbox of the Shield power-up
    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    // handle collision with other game objects (not implemented for Shield power-up)
    @Override
    public void collides(GameObject obj2) {
    }

    // check if the Shield power-up has been collected (always returns false)
    @Override
    public boolean hasCollided() {
        return false;
    }

    // draw the Shield power-up on the screen if it hasn't been collected
    public void drawImage(Graphics buffer) {
        if(!this.hasCollided) {
            buffer.drawImage(this.img, (int)x, (int)y, null);
        }
    }

    // apply the power-up effect to the Tank
    @Override
    public void applyPowerUp(Tank tank) {
        tank.hasShield();
        hasCollided = true; // mark the power-up as collected
    }
}
