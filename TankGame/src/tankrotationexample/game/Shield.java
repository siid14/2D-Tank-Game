package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shield extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    public Shield(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }
}
