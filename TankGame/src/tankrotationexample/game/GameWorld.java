package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Wall wall;
    private final Launcher lf;
    private long tick = 0;
    List<GameObject> gobjs = new ArrayList<>(800);
    private ResourceManager Resources;


    /**
     * Constructor to initialize the GameWorld.
     *
     * @param lf The launcher object responsible for starting the game.
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
        ResourceManager.loadResources(); // load all game resources (sprites, images, etc.).
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(); // update tank1's position and state.
                this.t2.update(); // update tank2's position and state.
                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        /**
         * 0 --> nothing
         * 9 --> unbreakables BUT non-collidable
         * 3 --> unbreakables
         * 2 --> breakables
         * 4 --> health
         * 5 --> speed
         * 6 --> shield
         */
        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("maps/map1.csv")));
        try(BufferedReader mapReader = new BufferedReader(isr)){
            int row = 0;
            String[] gameItems;
            while(mapReader.ready()){
                /*System.out.println((mapReader.readLine()));*/
                gameItems = mapReader.readLine().strip().split(";");
                for(int col = 0; col < gameItems.length; col++){
                    /*System.out.println(gameItems[col]);*/
                    String gameObject = gameItems[col];
                    if("0".equals(gameObject)) {
                        continue;
                    }
                    this.gobjs.add(GameObject.newInstance(gameObject, col*30, row*30));
                }
                row++;
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        // create and initialize tank1
        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        // create and initialize tank2
        t2 = new Tank(200, 800, 0, 0, (short) 180, ResourceManager.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0);
        this.lf.getJf().addKeyListener(tc2);
    }


    private void drawFloor(Graphics2D buffer){
        BufferedImage floor = Resources.getSprite("floor");
        for(int i = 0; i< GameConstants.GAME_SCREEN_WIDTH; i+=320){
            for (int j = 0; j < GameConstants.GAME_SCREEN_HEIGHT; j+=240){
                buffer.drawImage(floor,i,j,null);
            }
        }
    }

    private void renderMiniMap(Graphics2D g2, BufferedImage world){
        BufferedImage mm = world.getSubimage(
                0,
                0,
                GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT);
        g2.scale(0.2,0.2);
        g2.drawImage(mm,
                (GameConstants.GAME_SCREEN_HEIGHT*5)/2 - (GameConstants.GAME_SCREEN_WIDTH/2),
                (GameConstants.GAME_SCREEN_HEIGHT*5) - (GameConstants.GAME_SCREEN_HEIGHT)-190,
                null);
    }

    private void renderSplitScreens(Graphics2D g2, BufferedImage world){
        BufferedImage lh = world.getSubimage(
                (int) this.t1.getScreen_x(),
                (int) this.t1.getScreen_y(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rh = world.getSubimage(
                (int) this.t2.getScreen_x(),
                (int) this.t2.getScreen_y(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);
        /*g2.drawImage(world, 0, 0, null); // draw the buffer onto the main graphics*/


        g2.drawImage(lh, 0, 0, null);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2+4, 0, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

       /* buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);*/


        this.drawFloor(buffer);
        this.gobjs.forEach((gameObject -> gameObject.drawImage(buffer)));
        this.t1.drawImage(buffer); // draw tank1 on the buffer
        this.t2.drawImage(buffer); // draw tank2 on the buffer
        renderSplitScreens(g2, world);
        renderMiniMap(g2, world);



    }
}
