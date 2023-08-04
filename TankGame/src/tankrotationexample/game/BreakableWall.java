package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;

    public BreakableWall(float x, float y, BufferedImage img) {
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

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);

        // System.out.println("BreakableWall drawImage called at x: " + x + ", y: " + y);
    }


}
