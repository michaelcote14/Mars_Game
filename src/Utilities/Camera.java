package Utilities;

public class Camera {

    private float cameraX, cameraY;

    public Camera(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public void tick(GameObject object) {
        cameraX += ((object.getX() - cameraX) - 1000/2);
        cameraY += ((object.getY() - cameraY) - 563/2);

        // This stops the camera from going out of bounds
        if(cameraX <= 0) cameraX = 0;
        if(cameraX >= 1032) cameraX = 1032;
        if(cameraY <= 0) cameraY = 0;
        if(cameraY >= 563 + 48) cameraY = 563 + 48;
    }

    public void setCameraX(float cameraX) {
        this.cameraX = cameraX;
    }
    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }
    public float getCameraX() {
        return cameraX;
    }
    public float getCameraY() {
        return cameraY;
    }

}
