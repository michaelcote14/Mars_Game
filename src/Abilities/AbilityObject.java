package Abilities;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;

import java.awt.*;

public abstract class AbilityObject extends GameObject {
    protected float cooldown;
    protected float duration;

    public AbilityObject(float x, float y, ID id) {
        super(x, y, id);
    }
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
}
