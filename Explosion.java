/**
 * An animated explosion that will run after created and destroy itself after getting finished.
 */
public class Explosion extends AnimatedActor{
    private Animation anim;

    public Explosion(){
        String[] frameNames = new String[17];
        for(int i = 0; i < 17; i++){
            frameNames[i] = "img/ExplosionAnim/tile0" + i + ".png";
        }

        anim = new Animation(17, frameNames, true);
        anim.scale(80, 80);
        setAnimation(anim);
    }

    @Override
    public void act() {
        super.act();
    }
}
