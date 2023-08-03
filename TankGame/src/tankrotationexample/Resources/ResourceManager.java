package tankrotationexample.Resources;

import tankrotationexample.game.Bullet;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class ResourceManager {

    // maps to store loaded sprites, sounds, and animations
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();

    // map to store animation information (frame count) for each animation
    private static final Map<String, Integer> animationInfo = new HashMap<>() {{
        put("bullethit", 24);
        put("bulletshoot", 24);
        put("powerpick", 32);
        put("puffsmoke", 32);
        put("rocketflame", 16);
        put("rockethit", 32);
        /*put("bullet", 32);
        put("nuke", 24);*/
    }};


    /**
     * Load a sprite from the given path using ImageIO.read() method.
     *
     * @param path The path of the sprite to load.
     * @return The loaded BufferedImage representing the sprite.
     * @throws IOException If an error occurs while loading the sprite image.
     */
    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    /**
     * Initialize the sprites for the game by loading the necessary images.
     */
    private static void initSprites() {
        try {

            // load and store different sprites using loadSprite() method
            ResourceManager.sprites.put("tank1", loadSprite("tank/tank1.png"));
            ResourceManager.sprites.put("tank2", loadSprite("tank/tank2.png"));
            ResourceManager.sprites.put("bullet", loadSprite("bullet/bullet.jpg"));
            ResourceManager.sprites.put("rocket1", loadSprite("bullet/rocket1.png"));
            ResourceManager.sprites.put("rocket2", loadSprite("bullet/rocket2.png"));
            ResourceManager.sprites.put("break1", loadSprite("walls/break1.jpg"));
            ResourceManager.sprites.put("break2", loadSprite("walls/break2.jpg"));
            ResourceManager.sprites.put("unbreak", loadSprite("walls/unbreak.jpg"));
            ResourceManager.sprites.put("floor", loadSprite("floor/bg.bmp"));
            ResourceManager.sprites.put("health", loadSprite("powerups/health.png"));
            ResourceManager.sprites.put("shield", loadSprite("powerups/shield.png"));
            ResourceManager.sprites.put("speed", loadSprite("powerups/speed.png"));
            ResourceManager.sprites.put("menu", loadSprite("menu/title.png"));

            /*t = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource("menu/title.png"),"menu image is missing"));*/

        } catch (IOException e) {
            // throw a RuntimeException if there's an error loading any of the sprites
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the sprite for a given type.
     * @param type The type of sprite to get (e.g., "tank1", "tank2", "bullet", "menu").
     * @return The BufferedImage representing the sprite.
     * @throws RuntimeException If the specified sprite type is missing from the sprite resources.
     */
    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }

    public static List<BufferedImage> getAnimation(String type){
        if(!ResourceManager.animations.containsKey(type)){
            throw new RuntimeException("%s is missing from animations resources".formatted(type));
        }
        return ResourceManager.animations.get(type);
    }
    // * load animation frames
    private static void initAnimations(){
        String baseName = "animations/%s/%s_%04d.png";

        animationInfo.forEach((animationName, frameCount)->{
            /*System.out.println("Animation Name: " + animationName);
            System.out.println("Frame Count: " + frameCount);*/

            try {
                /*System.out.println("Loading animation: " + animationName);*/
                List<BufferedImage> frames = new ArrayList<>();
                for (int i = 0; i < frameCount ; i++) {
                     /*System.out.println(baseName.formatted(animationName,animationName,i));*/
                     String spritePath = baseName.formatted(animationName,animationName,i);
                     /*System.out.println("Loading frame: " + spritePath);*/
                     frames.add(loadSprite(spritePath));
                     /*System.out.println("Frame loaded.");*/
                    }
                ResourceManager.animations.put(animationName, frames);
                } catch (IOException e) {
                System.out.println("e: " + e);
                throw new RuntimeException(e);
            }
        });

    }
    /**
     * Load all necessary game resources.
     */
    public static void loadResources(){
        ResourceManager.initSprites();
        ResourceManager.initAnimations();
    }



    // * testing
    public static void main(String[] args) {
        /*ResourcePool<Bullet> bPool = new ResourcePool<>("bullet", 300);
        bPool.fillPool(Bullet.class, 300);*/
        /*ResourceManager.initSprites();*/
        /*ResourceManager.initAnimations();*/
        ResourceManager.loadResources();
        System.out.println();
    }

}
