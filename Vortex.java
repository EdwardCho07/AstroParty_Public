import mayflower.Actor;

import java.util.List;

/**
 * A class that follows a certain path and instantly destroys the player if it touches them.
 */
public class Vortex extends AnimatedActor{
    private int[] xPath;
    private int[] yPath;

    private int currentTarget;
    private final int speed = 2;

    private Animation anim;

    //adjust path and look at bounds
    public Vortex(){
        String[] pathNames = new String[8];
        for(int i = 0; i < 5; i++){
            pathNames[i] = "img/VortexAnim/" + (i+1) + ".png";
        }
        pathNames[5] = "img/VortexAnim/" + 4 + ".png";
        pathNames[6] = "img/VortexAnim/" + 3 + ".png";
        pathNames[7] = "img/VortexAnim/" + 2 + ".png";

        setPath();
        anim = new Animation(6, pathNames);
        anim.setBounds(30, 30, 90, 90);
        anim.scale(200, 200);
        setAnimation(anim);
        currentTarget = 0;
    }

    /**
     * A method to manually set the path the vortex will travel on.
     */
    private void setPath(){
        xPath = new int[8];
        yPath = new int[8];

        xPath[0] = 320;
        xPath[1] = 320;
        xPath[2] = 1080;
        xPath[3] = 1080;
        xPath[4] = 1680;
        xPath[5] = 1680;
        xPath[6] = 1080;
        xPath[7] = 1080;

        yPath[0] = 250;
        yPath[1] = 920;
        yPath[2] = 920;
        yPath[3] = 250;
        yPath[4] = 250;
        yPath[5] = 920;
        yPath[6] = 920;
        yPath[7] = 250;
    }

    @Override
    public void act() {
        super.act();

        if (isTouching(Actor.class)){
            List<Actor> list = getIntersectingObjects(Actor.class);
            for(Actor a : list){
                if(a instanceof DamageableEntity){
                    ((DamageableEntity) a).GetDamaged(1000);
                }
            }
        }

        turnTowards(xPath[currentTarget], yPath[currentTarget]);
        move(speed);

        if(xPath[currentTarget] == getX() && yPath[currentTarget] == getY()){
            currentTarget = (currentTarget + 1) % xPath.length;
        }
    }
}
