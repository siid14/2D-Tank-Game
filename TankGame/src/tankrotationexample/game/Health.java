package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    boolean hasCollided = false;

    public Health(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {

    }

    @Override
    public boolean hasCollided() {
        return false;
    }

    public void drawImage(Graphics buffer) {
        if(!this.hasCollided) {
            buffer.drawImage(this.img, (int)x, (int)y, null);
        }
    }
}
