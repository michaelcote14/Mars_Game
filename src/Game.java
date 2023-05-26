import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1000, HEIGHT = 563;
    private boolean gameRunning = false;
    private Thread thread;
    private ObjectHandler oHandler;
    private HUD hud;
    private Spawner spawner;
    private Camera camera;
    private ImageSheet iSheet;

    private BufferedImage level = null;
    private BufferedImage imageSheet = null;
    private BufferedImage floor = null;

    public Game(){
        new Window(WIDTH, HEIGHT, "Wizard Game", this);
        gameStart();

        oHandler = new ObjectHandler();
        camera = new Camera(0, 0); // makes the camera start at 0,0
        hud = new HUD();
        spawner = new Spawner(oHandler, hud, camera);

        this.addKeyListener(new KeyHandler(oHandler));

        BufferedImageLoader imageLoader = new BufferedImageLoader();
        level = imageLoader.loadImage("/wizard_level.png");
        imageSheet = imageLoader.loadImage("/image_sheet.png");

        iSheet = new ImageSheet(imageSheet);

        floor = iSheet.grabImage(4, 2, 32, 32);

        this.addMouseListener(new mouseHandler(oHandler, camera, iSheet, this));

        loadLevel(level);


    }
    private void gameStart(){
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    private void gameStop(){
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
    public void tick() {
        // this gets updated 60 times per second, and is for updating

        // this makes the camera center on the player
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) {
                camera.tick(oHandler.object.get(i));
            }
        }

        oHandler.tick();
        hud.tick();
        spawner.tick();

    }
    public void render() {
        // this gets updated 2000 times per second
        BufferStrategy bufferStrat = this.getBufferStrategy();
        if(bufferStrat == null){
            this.createBufferStrategy(3); // this creates 3 buffers, which is recommended
            return;
        }
        Graphics g = bufferStrat.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g; // this is for the camera

        ////////////// Put all graphics between here
        g2d.translate(-camera.getCameraX(), -camera.getCameraY()); // this is for the camera

        // this is the background
        for(int xx = 0; xx < 30*72; xx+=32) {
            for(int yy = 0; yy < 30*72; yy+=32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        oHandler.render(g);

        g2d.translate(camera.getCameraX(), camera.getCameraY()); // this is for the camera
        hud.render(g);

        ////////////// and here

        g.dispose();
        bufferStrat.show();
    }
    // loading the level
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0; xx < w; xx++) {
            for(int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                if(red == 255) {
                    oHandler.addObject(new Block(xx*32, yy*32, ID.Block, iSheet));
                }
                else if(blue == 255 && green == 0) {
                    oHandler.addObject(new Wizard(xx*32, yy*32, ID.Player, oHandler, this, iSheet));
                }
                else if(green == 255 && blue == 0) {
                    oHandler.addObject(new BasicEnemy(xx*32, yy*32, ID.BasicEnemy, oHandler, iSheet));
                }
                else if(green == 255 && blue == 255) {
                    oHandler.addObject(new Crate(xx * 32, yy * 32, ID.Crate, iSheet));
                }

            }
        }
    }
    public static int clamp(int var, int min, int max) {
        // this method keeps objects from going out of bounds
        if(var >= max) return var = max;
        else if(var <= min) return var = min;
        else return var;
    }

    public static void main(String[] args) {
        new Game();
    }
}
