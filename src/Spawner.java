import java.awt.image.BufferedImage;

public class Spawner {
    private ObjectHandler oHandler;
    private HUD hud;
    private Camera camera;
    private ImageSheet iSheet;
    private BufferedImage levelImage;

    public Spawner(ObjectHandler oHandler, HUD hud, Camera camera, ImageSheet iSheet, BufferedImage levelImage) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.camera = camera;
        this.iSheet = iSheet;
        this.levelImage = levelImage;
    }

    public void tick() {
        // todo make it to where every level increase just respawns the enemies

        if(hud.getLevel() == 2 && hud.isNewLevel == true) {
            hud.setLevel(3);
            System.out.println("Level 2");

            int w = levelImage.getWidth();
            int h = levelImage.getHeight();

            for(int xx = 0; xx < w; xx++) {
                for(int yy = 0; yy < h; yy++) {
                    int pixel = levelImage.getRGB(xx, yy);
                    int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                    int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                    if(green == 255 && blue == 0) {
                        oHandler.addObject(new BasicEnemy(xx*32, yy*32, ID.BasicEnemy, oHandler, iSheet));
                    }
//                    else if(green == 255 && blue == 255) {
//                        oHandler.addObject(new Crate(xx * 32, yy * 32, ID.Crate, iSheet));
//                    }

                }
            }
            hud.isNewLevel = false;

        }

//        if(killTracker >= hud.getLevel()) {
//            killTracker = 0;
//            hud.setLevel(hud.getLevel() + 1);
//            if(hud.getLevel() == 2) { // you can use this somehow
//                oHandler.addObject(new BasicEnemy((int) (Math.random() * camera.getCameraX()), (int) (Math.random() * camera.getCameraY()), ID.BasicEnemy, oHandler, null ));
//            }
//            else if(hud.getLevel() == 3) { // you can use this somehow
//                oHandler.addObject(new FastEnemy((int) (Math.random() * Game.WIDTH), (int) (Math.random() * Game.HEIGHT), ID.BasicEnemy, oHandler));
//            }
    }
}

