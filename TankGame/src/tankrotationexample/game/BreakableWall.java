package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    private int life;
    private GameWorld gw;

    // constructor for creating a BreakableWall object
    public BreakableWall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
        this.life = 50;
    }

    // get the hitbox of the BreakableWall
    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    // handle collision with other game objects
    @Override
    public void collides(GameObject obj) {
        if(obj instanceof Bullet){
            // decrease the life of the wall upon collision with a bullet
            this.life--;
        }
    }

    // check if the wall has been destroyed
    @Override
    public boolean hasCollided() {
        if(this.life <= 0){
            return true; // wall has collided if its life is zero or negative
        }
        return false; // wall hasn't collided yet
    }

    // draw the BreakableWall on the screen
    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }


}
