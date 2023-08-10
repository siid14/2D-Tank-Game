package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    List<GameObject> gameObjects = new ArrayList<>(800);
    private ResourceManager Resources;
    List<Animation> anims = new ArrayList<>();
    Sound background = ResourceManager.getSound("background");


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

            // play background music when the game run
            background.setLooping();
            background.playSound();

            while (true) {
                this.tick++;
                this.t1.update(this); // update tank1's position and state.
                this.t2.update(this); // update tank2's position and state.
                this.anims.forEach(animation -> animation.update());
                this.checkCollision();
                this.gameObjects.removeIf(gameObject -> gameObject.hasCollided());
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
     * Checks for collisions between game objects in the game world.
     * If a collision is detected, it calls the 'collides' method of the first object
     * and passes the second object as an argument.
     */
    private void checkCollision() {
        // iterate through each game object in the gameObjects list
        for(int i = 0; i < this.gameObjects.size(); i++){
            GameObject obj1 = this.gameObjects.get(i);

            // skip unmovable object types from collision detection
            if(obj1 instanceof Wall || obj1 instanceof Health || obj1 instanceof Speed || obj1 instanceof Shield){
                continue;
            }

            // check for collisions with other game objects in the gameObjects list
            for(int j = 0; j < this.gameObjects.size(); j++){
                if(i==j) continue;
                GameObject obj2 = this.gameObjects.get(j);

                // skip collisions with 'Tank' objects since they are handled separately
                if(obj2 instanceof Tank){
                    continue;
                }

                // if a collision is detected between the hit-box of obj1 and obj2
                if(obj1.getHitBox().intersects(obj2.getHitBox())){

                    obj1.collides(obj2);

                    // System.out.println("Collision detected: " +obj1 + " has hit " + obj2);
                    if(obj1 instanceof Tank && obj2 instanceof Health){
                        System.out.println("Tank has hit health");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if(obj1 instanceof Tank && obj2 instanceof Shield){
                        System.out.println("Tank has hit health");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if(obj1 instanceof Tank && obj2 instanceof Speed){
                        System.out.println("Tank has hit health");
                        ResourceManager.getSound("pickup").playSound();
                    }

                    if(obj1 instanceof Tank && obj2 instanceof Bullet){
                        System.out.println("Tank has been hit by bullet");
                        ResourceManager.getSound("explosion").playSound();
                    }

                    if(obj1 instanceof Bullet && obj2 instanceof BreakableWall){
                        System.out.println("BreakableWall has been hit by bullet 2");
                        ResourceManager.getSound("explosion").playSound();
                    }
                }
            }
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
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        /**
         * 0 --> nothing
         * 9 --> unbreakables BUT non-collidable
         * 3 --> unbreakables
         * 2 --> breakables
         * 4 --> health
         * 5 --> speed
         * 6 --> shield
         *  Load all resources for Tank Wars Game. Set all Game Objects to their
         *  initial state as well.
         */
        InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(
                        ResourceManager
                                .class
                                .getClassLoader()
                                .getResourceAsStream("maps/map1.csv"))
        );
        /*this.anims.add(new Animation(300, 300, ResourceManager.getAnimation("bullethit")));
        this.anims.add(new Animation(350, 300, ResourceManager.getAnimation("bulletshoot")));
        this.anims.add(new Animation(400, 300, ResourceManager.getAnimation("powerpick")));
        this.anims.add(new Animation(450, 300, ResourceManager.getAnimation("puffsmoke")));
        this.anims.add(new Animation(500, 300, ResourceManager.getAnimation("rocketflame")));
        this.anims.add(new Animation(550, 300, ResourceManager.getAnimation("rockethit")));*/


        // create the game world buffer
        try(BufferedReader mapReader = new BufferedReader(isr)){
            int row = 0;
            String[] gameItems;
            // read the map from a CSV file and create game objects accordingly
            while(mapReader.ready()){
                /*System.out.println((mapReader.readLine()));*/
                gameItems = mapReader.readLine().strip().split(";");
                for(int col = 0; col < gameItems.length; col++){
                    /*System.out.println(gameItems[col]);*/
                    String gameObject = gameItems[col];
                    if("0".equals(gameObject)) {
                        continue;
                    }
                    // create game object based on the code in the CSV and add it to the list
                    this.gameObjects.add(GameObject.newInstance(gameObject, col*30, row*30));
                }
                row++;
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        // create and initialize tank1
        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank1"), lf, "T. Red");
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        // create and initialize tank2
        t2 = new Tank(1400, 1000, 0, 0, (short) 0, ResourceManager.getSprite("tank2"), lf, "T. Blue");
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_V);
        this.lf.getJf().addKeyListener(tc2);

        this.gameObjects.add(t1);

        this.gameObjects.add(t2);
    }

    /**
     * Draw the floor tiles on the game buffer.
     *
     * @param buffer The Graphics2D buffer on which to draw the floor.
     */
    private void drawFloor(Graphics2D buffer){
        // get the floor tile image from the resource manager
        BufferedImage floor = Resources.getSprite("floor");

        // loop to draw the floor tiles on the game buffer
        for(int i = 0; i< GameConstants.GAME_WORLD_WIDTH; i+=320){
            for (int j = 0; j < GameConstants.GAME_WORLD_HEIGHT; j+=240){
                // draw the floor tile image at the specified position (i, j) on the buffer
                buffer.drawImage(floor,i,j,null);
            }
        }
    }

    // * to render the minimap
    private void renderMiniMap(Graphics2D g2, BufferedImage world){
        // create a sub-image from the 'world' image, representing the entire game world
        BufferedImage minimap = world.getSubimage(
                0,
                0,
                GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT);

        // scale the Graphics2D object 'g2' by a factor of 0.2 in both x and y directions
        // this is done to reduce the size of the minimap to 20% of its original size
        g2.scale(0.2,0.2);

        // calculate the x-coordinate for drawing the minimap at the center of the screen horizontally
        int minimapX = (GameConstants.GAME_SCREEN_HEIGHT * 5) / 2 - (GameConstants.GAME_WORLD_WIDTH / 11);

        // calculate the y-coordinate for drawing the minimap at the bottom of the screen
        // the minimap is adjusted 250 units above the bottom of the screen
        int minimapY = (GameConstants.GAME_SCREEN_HEIGHT * 5) - (GameConstants.GAME_WORLD_HEIGHT) - 250;

        // draw the minimap on the Graphics2D object 'g2' at the calculated position (minimapX, minimapY)
        g2.drawImage(minimap, minimapX, minimapY, null);
    }


    private void renderSplitScreens(Graphics2D g2, BufferedImage world){
        // get the left-half split screen image for tank1
        BufferedImage lh = world.getSubimage(
                (int) this.t1.getScreen_x(), // x-coordinate of the top-left corner of the left split screen
                (int) this.t1.getScreen_y(), // y-coordinate of the top-left corner of the left split screen
                GameConstants.GAME_SCREEN_WIDTH/2, // width of the left split screen (half of the game screen width)
                GameConstants.GAME_SCREEN_HEIGHT); // height of the left split screen (full game screen height)

        // get the right-half split screen image for tank2
        BufferedImage rh = world.getSubimage(
                (int) this.t2.getScreen_x(),
                (int) this.t2.getScreen_y(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        /*g2.drawImage(world, 0, 0, null); // draw the buffer onto the main graphics*/

        // draw the left and right split screens onto the main graphics
        g2.drawImage(lh, 0, 0, null);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2+4, 0, null);
    }

    public void addGameObject(Bullet bullet) {
        this.gameObjects.add(bullet);
    }



    // * paint the game components on the screen
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

       /* buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);*/

        // draw the floor tiles on the game buffer
        this.drawFloor(buffer);

        // draw all game objects on the buffer
        this.gameObjects.forEach((gameObject -> gameObject.drawImage(buffer)));

        // draw tank1 and tank2 on the buffer
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        this.anims.forEach(animation -> animation.drawImage(buffer));

        // render split screens for tank1 and tank2 on the main graphics
        renderSplitScreens(g2, world);

        // render the minimap on the main graphics
        renderMiniMap(g2, world);
    }
}
