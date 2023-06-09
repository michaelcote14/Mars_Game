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
        if(cameraX >= 22500) cameraX = 22500; // this is the width of the map
        if(cameraY <= 0) cameraY = 0;
        if(cameraY >= 22500) cameraY = 22500; // this is the height of the map
    }
    public float getPlayerX() {
        return cameraX;
    }
    public float getPlayerY() {
        return cameraY;
    }

}
