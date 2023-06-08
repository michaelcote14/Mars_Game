package Main;

import Utilities.Camera;
import Utilities.GameObject;
import Utilities.ImageHandler;

public class MapHandler {
    private float playerX, playerY;
    private boolean wasMapLoaded = false;

    public MapHandler() {
    }
    public void tick(GameObject object, Game game) {
        playerX = object.getX();
        playerY = object.getY();
        System.out.println(playerX + " " + playerY);
        if(playerX > 1900 && !wasMapLoaded) {
            game.loadMap(ImageHandler.images.get("map1"));
            wasMapLoaded = true;
        }
    }
}
