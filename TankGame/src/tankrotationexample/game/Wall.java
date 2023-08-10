package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;

    // constructor for creating a Wall object
    public Wall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    // get the hitbox of the Wall
    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    // handle collision with other game objects (not implemented for Wall)
    @Override
    public void collides(GameObject obj2) {

    }

    // check if the Wall has collided with something (always returns false)
    @Override
    public boolean hasCollided() {
        return false;
    }

    // draw the Wall on the screen
    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }

    // get the X coordinate of the Wall
    public float getX() {
        return this.x;
    }

    // Get the Y coordinate of the Wall
    public float getY() {
        return this.y;
    }
}
