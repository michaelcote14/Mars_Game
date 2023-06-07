package Objects;

import Utilities.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage explosionImage1, explosionImage2, explosionImage3, explosionImage4, explosionImage5, explosionImage6, explosionImage7, explosionImage8, explosionImage9, explosionImage10, explosionImage11, explosionImage12, explosionImage13, explosionImage14, explosionImage15, explosionImage16, explosionImage17, explosionImage18;
    private BufferedImage[] explosionImages = new BufferedImage[18];
    private Animation explosionAnim;

    public Explosion(float x, float y, ID id, ObjectHandler oHandler, ImageHandler imageHandler) {
        super(x, y, id, imageHandler);
        this.oHandler = oHandler;
        this.explosionImage1 = ImageHandler.images.get("fireExplosion1");
        this.explosionImage2 = ImageHandler.images.get("fireExplosion2");
        this.explosionImage3 = ImageHandler.images.get("fireExplosion3");
        this.explosionImage4 = ImageHandler.images.get("fireExplosion4");
        this.explosionImage5 = ImageHandler.images.get("fireExplosion5");
        this.explosionImage6 = ImageHandler.images.get("fireExplosion6");
        this.explosionImage7 = ImageHandler.images.get("fireExplosion7");
        this.explosionImage8 = ImageHandler.images.get("fireExplosion8");
        this.explosionImage9 = ImageHandler.images.get("fireExplosion9");
        this.explosionImage10 = ImageHandler.images.get("fireExplosion10");
        this.explosionImage11 = ImageHandler.images.get("fireExplosion11");
        this.explosionImage12 = ImageHandler.images.get("fireExplosion12");
        this.explosionImage13 = ImageHandler.images.get("fireExplosion13");
        this.explosionImage14 = ImageHandler.images.get("fireExplosion14");
        this.explosionImage15 = ImageHandler.images.get("fireExplosion15");
        this.explosionImage16 = ImageHandler.images.get("fireExplosion16");
        this.explosionImage17 = ImageHandler.images.get("fireExplosion17");
        this.explosionImage18 = ImageHandler.images.get("fireExplosion18");
        explosionImages[0] = explosionImage1;
        explosionImages[1] = explosionImage2;
        explosionImages[2] = explosionImage3;
        explosionImages[3] = explosionImage4;
        explosionImages[4] = explosionImage5;
        explosionImages[5] = explosionImage6;
        explosionImages[6] = explosionImage7;
        explosionImages[7] = explosionImage8;
        explosionImages[8] = explosionImage9;
        explosionImages[9] = explosionImage10;
        explosionImages[10] = explosionImage11;
        explosionImages[11] = explosionImage12;
        explosionImages[12] = explosionImage13;
        explosionImages[13] = explosionImage14;
        explosionImages[14] = explosionImage15;
        explosionImages[15] = explosionImage16;
        explosionImages[16] = explosionImage17;
        explosionImages[17] = explosionImage18;
        explosionAnim = new Animation(250, explosionImages);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
            explosionAnim.runAnimation();
            explosionAnim.drawAnimation(g, (int) x-10, (int) y-65, 50, 210, 210);
            if(explosionAnim.getCount() == 18) {
                oHandler.removeObject(this);
            }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x-60, (int) y-70, 210, 210);
    }

}

