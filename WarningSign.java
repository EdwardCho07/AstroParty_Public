import mayflower.Actor;
import mayflower.MayflowerImage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This is a warning sign that flashes and destroys itself before an environmental laser is shot.
 */
public class WarningSign extends Actor {
    private Timer destroyTimer;
    private Timer flashTimer;

    private boolean isFadingOut;
    private MayflowerImage image;

    private WarningSign instance;

    public WarningSign(int duration){
        image = new MayflowerImage("img/Tiles/Warning.png");
        image.scale(60, 60);

        setImage(image);

        isFadingOut = true;
        destroyTimer = new Timer();
        flashTimer = new Timer();
        instance = this;

        destroyTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getWorld().removeObject(instance);
            }
        }, duration);
    }

    @Override
    public void act() {
        if(isFadingOut){
            int u = image.getTransparency() - 4;
            if (u <= 0) {
                u = 0;
                isFadingOut = false;
            }
            image.setTransparency(u);
            setImage(image);
        }
        else {
            int u = image.getTransparency() + 4;
            if (u >= 100) {
                u = 100;
                isFadingOut = true;
            }

            image.setTransparency(u);
            setImage(image);
        }
    }
}
