import java.awt.image.BufferedImage;

public class ImageSheet {

    private BufferedImage image;

    public ImageSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        // you will have to adjust this if you want to use a different sprite sheet
        return image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }
}
