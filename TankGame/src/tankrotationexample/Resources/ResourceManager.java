package tankrotationexample.Resources;

import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.ResolverStyle;
import java.util.*;

public class ResourceManager {

    // maps to store loaded sprites, sounds, and animations
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
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

    private static Sound loadSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // get the resource from class loader from a path
        AudioInputStream ais = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(
                        ResourceManager.class.getClassLoader().getResource(path)
                        )
                );
        Clip c = AudioSystem.getClip(); // make new clip
        c.open(ais); // open the clip
        Sound s = new Sound(c); // get that clip into Sound object
        s.setVolume(.2f);
        return s;
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

    public static Sound getSound(String type){
        if(!ResourceManager.sounds.containsKey(type)){
            throw new RuntimeException("%s is missing from sounds resources".formatted(type));
        }
        return ResourceManager.sounds.get(type);
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

    public static void initSounds(){
        try {
            ResourceManager.sounds.put("bullet_shoot", loadSound("sounds/bullet_shoot.wav"));
            ResourceManager.sounds.put("explosion", loadSound("sounds/explosion.wav"));
            ResourceManager.sounds.put("background", loadSound("sounds/Music.mid"));
            /*ResourceManager.sounds.put("background", loadSound("sounds/Pokeball.mid"));*/
            ResourceManager.sounds.put("pickup", loadSound("sounds/pickup.wav"));
            ResourceManager.sounds.put("shotfire", loadSound("sounds/shotfiring.wav"));
        } catch(Exception e){
            System.out.println("initSounds() e : " + e);
        }

    }

    /**
     * Load all necessary game resources.
     */
    public static void loadResources(){
        ResourceManager.initSprites();
        ResourceManager.initAnimations();
        ResourceManager.initSounds();
    }



    // * testing
    public static void main(String[] args) {
        /*ResourcePool<Bullet> bPool = new ResourcePool<>("bullet", 300);
        bPool.fillPool(Bullet.class, 300);*/
        /*ResourceManager.initSprites();*/
        /*ResourceManager.initAnimations();*/
        ResourceManager.loadResources();
        Sound background = ResourceManager.getSound("background");
        /*background.setLooping();
        background.playSound();*/
        while (true){
            /*System.out.println();*/

            try{
                ResourceManager.getSound("explosion").playSound();

                Thread.sleep(1500);
                ResourceManager.getSound("bullet_shoot").playSound();
                Thread.sleep(1500);
                ResourceManager.getSound("pickup").playSound();
                Thread.sleep(1500);
                ResourceManager.getSound("shotfire").playSound();
             } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
