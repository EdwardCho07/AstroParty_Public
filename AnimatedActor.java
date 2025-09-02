import mayflower.Actor;
import mayflower.MayflowerImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that is used to create actors that use animation.
 */
public class AnimatedActor extends Actor{
    private Animation anim;
    private Timer animTimer;

    private AnimatedActor instance;
    private TimerTask animTask;
    
    public AnimatedActor(){
        animTimer = new Timer();
        instance = this;
        animTask = null;
    }

    /**
     * Starts the animation running.
     */
    private void StartAnim(){
        if(animTask != null){
            animTask.cancel();
        }

        animTask = new TimerTask() {
            @Override
            public void run() {
                MayflowerImage img = anim.getNextFrame();

                if(img == null){
                    getWorld().removeObject(instance);
                    animTask.cancel();
                }
                else{
                    setImage(img);
                }                
            }
        };

        animTimer.schedule(animTask, 1000 / anim.getFrameRate(), 1000 / anim.getFrameRate());
    }
    
    public void act(){ 

    }

    /**
     * Sets the animation for the AnimatedActor to use.
     * @param a The animation for this AnimatedActor to use.
     */
    public void setAnimation(Animation a){
        anim = a;
        StartAnim();
    }
}
