package Main;

import Abilities.Abilities;
import Objects.Block;
import Objects.BuzzSaw;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1000, HEIGHT = 563;
    private boolean gameRunning = false;
    private Thread thread;
    public static boolean paused = false;

    public Player player;
    private ObjectHandler oHandler;
    private HUD hud;
    private Menu menu;
    private Shop shop;
    private Spawner spawner;
    private Camera camera;
    private ImageHandler iHandler;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Abilities abilities;

    private BufferedImage levelImage = null;
    private BufferedImage imageSheet = null;
    private BufferedImage floor = null;
    public boolean wasLevelLoaded = false;

    public enum STATE {
        Menu,
        Game,
        Shop,
        Help,
        Death,
    }

    public static STATE gameState = STATE.Menu; // this is the default starting state

    public Game() {
        new Window(WIDTH, HEIGHT, "Mars Game", this);

        // All of these statements below need to be handled carefully, as the timing of them is important
        oHandler = new ObjectHandler();
        iHandler = new ImageHandler();
        camera = new Camera(0, 0); // makes the camera start at 0,0
        this.keyHandler = new KeyHandler(oHandler, this);
        hud = new HUD(iHandler, this.keyHandler);
        menu = new Menu(this, oHandler, hud);
        shop = new Shop(oHandler, hud, this, player);
        abilities = new Abilities();

        this.addKeyListener(this.keyHandler);

        BufferedImageLoader imageLoader = new BufferedImageLoader();
        levelImage = imageLoader.loadImage("/Objects/level1.png");
        imageSheet = imageLoader.loadImage("/Objects/imageSheet.png");
        spawner = new Spawner(oHandler, hud, camera, iHandler, levelImage);
        floor = ImageHandler.images.get("jailFloor");

        this.mouseHandler = new MouseHandler(oHandler, iHandler, this);
        this.addMouseListener(this.mouseHandler);
        this.addMouseListener(menu);
        this.addMouseListener(shop);

        gameStart();
    }

    public void tick() {
        // this gets updated 60 times per second, and is for updating
        if (gameState == STATE.Game) {
            if (!paused) {
                oHandler.tick();
                hud.tick();
            }
            if (spawner != null) {
                spawner.tick();
            }
            // this makes the camera center on the player
            for (int i = 0; i < oHandler.object.size(); i++) {
                if (oHandler.object.get(i).getId() == ID.Player) {
                    camera.tick(oHandler.object.get(i));
                }
            }
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Death) {
            menu.tick();
            oHandler.tick();
        }
    }

    public void render() {
        // this gets updated 2000 times per second
        BufferStrategy bufferStrat = this.getBufferStrategy();
        if (bufferStrat == null) {
            this.createBufferStrategy(3); // this creates 3 buffers, which is recommended
            return;
        }
        Graphics g = bufferStrat.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g; // this is for the camera

        ////////////// Put all graphics between here
        g2d.translate(-camera.getPlayerX(), -camera.getPlayerY()); // this is for the camera

        //  this is the background
        for (int xx = 0; xx < 30 * 72; xx += 32) {
            for (int yy = 0; yy < 30 * 72; yy += 32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        if (paused) {
            g.setColor(Color.BLACK);
            g.fillRect((int)camera.getPlayerX(), (int)camera.getPlayerY(), Game.WIDTH, Game.HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", 1, 40));
            g.drawString("What's wrong baby? Need a break?", Game.WIDTH / 2 - 100, Game.HEIGHT / 2);
            g.setFont(new Font("arial", 1, 30));
        }
        else if (gameState == STATE.Game) {

            if (wasLevelLoaded == false) {
                BufferedImageLoader imageLoader = new BufferedImageLoader();
                levelImage = imageLoader.loadImage("/Objects/level1.png");
                loadLevel(levelImage);
                wasLevelLoaded = true;
            }
            oHandler.render(g);
            g2d.translate(camera.getPlayerX(), camera.getPlayerY()); // this is for the camera
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Death) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            oHandler.render(g);
            g2d.translate(camera.getPlayerX(), camera.getPlayerY()); // this is for the camera
            menu.render(g);
        } else if (gameState == STATE.Shop) {
            g2d.translate(camera.getPlayerX(), camera.getPlayerY()); // this is for the camera
            shop.render(g);
        }

        ////////////// and here

        g.dispose();
        bufferStrat.show();
    }

    public void loadLevel(BufferedImage levelImage) {
        int w = levelImage.getWidth();
        int h = levelImage.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = levelImage.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                if (red == 255 && green == 0 && blue == 0) {
                    oHandler.addObject(new Block(xx * 32, yy * 32, ID.Block, iHandler));
                }
                else if (red == 00 && green == 00 && blue == 255) {
                    player = new Player(xx * 32, yy * 32, ID.Player, oHandler, this, iHandler, camera, this.keyHandler, this.mouseHandler);
                    oHandler.addObject(player);
                    SaveOrLoad.load("Save1", player);
                }
//              else if (green == 255 && blue == 255) {
//                    oHandler.addObject(new HealthPack(xx * 32, yy * 32, ID.Crate, oHandler, iSheet));
//                }
                else if(green == 100 && red == 100 && blue == 0) {
                    oHandler.addObject(new BuzzSaw(xx * 32, yy * 32, ID.BuzzSawTrap, oHandler, iHandler));
                }
            }
        }
    }
    private void gameStart() {
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void gameStop() {
        gameRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus(); // this means that we don't have to click on the screen to use the keyboard
        // this is just the most popular game loop, you don't have to understand it
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0; // how many times we want to tick per second
        double ns = 1000000000 / amountOfTicks; // how many nanoseconds we have to get to reach 60 ticks per second
        double delta = 0; // how many nanoseconds have gone by
        long timer = System.currentTimeMillis();
        int frames = 0; // how many frames we are getting per second
        while (gameRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; // how many nanoseconds have gone by divided by how many nanoseconds we need to get to 60 ticks per second
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (gameRunning) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames); // prints out how many frames we are getting per second
                frames = 0;
            }
        }
        gameStop();
    }

    public static void main(String[] args) {
        new Game();
    }
}
