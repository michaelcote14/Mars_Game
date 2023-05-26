public class Spawner {

    private ObjectHandler oHandler;
    private HUD hud;
    private Camera camera;

    private int killTracker = 0;

    public Spawner(ObjectHandler oHandler, HUD hud, Camera camera) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.camera = camera;
    }

    public void tick() {
        // if you kill an enemy, increase kill tracker
        killTracker++;

        if(killTracker >= hud.getLevel()) {
            killTracker = 0;
            hud.setLevel(hud.getLevel() + 1);
            if(hud.getLevel() == 2) { // you can use this somehow
                oHandler.addObject(new BasicEnemy((int) (Math.random() * camera.getCameraX()), (int) (Math.random() * camera.getCameraY()), ID.BasicEnemy, oHandler, null ));
            }
//            else if(hud.getLevel() == 3) { // you can use this somehow
//                oHandler.addObject(new FastEnemy((int) (Math.random() * Game.WIDTH), (int) (Math.random() * Game.HEIGHT), ID.BasicEnemy, oHandler));
//            }
        }
    }
}
