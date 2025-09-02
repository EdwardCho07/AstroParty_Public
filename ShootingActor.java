import mayflower.Keyboard;
import mayflower.Mayflower;
import java.util.Timer;

import java.util.List;
import java.util.Stack;
import java.util.TimerTask;

/**
 * The shooting functionality for a player. This is the class that should be used to create a player.
 */
public class ShootingActor extends Player {
    //Shooting config
    private final int distance = 50;

    private final Timer shootTimer;
    private final Timer tripleShotTimer;
    private final Timer continuousShotTimer;

    private final int shootCooldown = 250;
    private final int shootCooldownContinuous = 20;
    private final int tripleShotLength = 10000;
    private final int continuousShotLength = 5000;

    private boolean tripleShotEnabled;
    private boolean continuousShotEnabled;
    private boolean canShoot;

    //Consumables config
    private final int maxConsumableAmount = 5;
    private Stack<ConsumableType> consumableStack;

    //Key binds
    private int shootKey;
    private int consumableKey;

    //Instance
    private ShootingActor instance;

    public ShootingActor(int playerNum){
        super(playerNum);
        setRotation(180);
        if(playerNum == 1){
            shootKey = Keyboard.KEY_W;
            consumableKey = Keyboard.KEY_S;
        }
        else if(playerNum == 2){
            shootKey = Keyboard.KEY_UP;
            consumableKey = Keyboard.KEY_DOWN;
        }

        shootTimer = new Timer();
        tripleShotTimer = new Timer();
        continuousShotTimer = new Timer();

        consumableStack = new Stack<ConsumableType>();

        tripleShotEnabled = false;
        continuousShotEnabled = false;
        canShoot = true;

        instance = this;
    }

    @Override
    public void act() {
        super.act();
        //Shoot if cooldown is over.
        if(!continuousShotEnabled && Mayflower.isKeyPressed(shootKey) && canShoot){
            Shoot();
        }
        else if (continuousShotEnabled && Mayflower.isKeyDown(shootKey) && canShoot) {
            Shoot();
        }

        //Use consumable
        if(Mayflower.isKeyPressed(consumableKey) && !consumableStack.isEmpty()){
            ConsumableType consumableToUse = consumableStack.pop();
            switch (consumableToUse){
                case Laser:
                    int[] transform = getLaserTransform();
                    getWorld().addObject(new Laser(getRotation(), transform[2], this, pNum), transform[0], transform[1]);
                    recoil();
                    break;
                case Mine:
                    getWorld().addObject(new Mine(this), getCenterX(), getCenterY());
                    break;
                case TripleShot:
                    tripleShotEnabled = true;
                    tripleShotTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            instance.tripleShotEnabled = false;
                        }
                    }, tripleShotLength);
                    break;
                case ContinuousShot:
                    continuousShotEnabled = true;
                    continuousShotTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            instance.continuousShotEnabled = false;
                        }
                    }, continuousShotLength);
                    break;
            }
        }

        //Pick up consumable if touching one.
        List<Consumable> consumables = getIntersectingObjects(Consumable.class);
        if(!consumables.isEmpty() && consumableStack.size() < maxConsumableAmount){
            for(Consumable c : consumables){
                c.pickConsumable(consumableStack);
            }
        }
    }

    /**
     * Makes the ShootingActor shoot a bullet together with power ups involving shooting.
     */
    private void Shoot() {
        canShoot = false;
        int cooldown = continuousShotEnabled ? shootCooldownContinuous : shootCooldown;
        shootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                instance.canShoot = true;
            }
        }, shootCooldown);

        int[] position = getBulletSpawnLocation();
        getWorld().addObject(new Bullet(getRotation(), 10, pNum, this), position[0], position[1]);
        Mayflower.playSound("sound/ship/shoot.wav");

        if(tripleShotEnabled){
            int bullet3rotation = getRotation() - 15;
            if(bullet3rotation < 0) bullet3rotation = 360 + bullet3rotation;

            getWorld().addObject(new Bullet((getRotation() + 15) % 360, 10, pNum, this), position[0], position[1]);
            getWorld().addObject(new Bullet(bullet3rotation, 10, pNum, this), position[0], position[1]);
        }
    }

    /**
     * Finds the location to spawn the laser in and its length.
     * @return An int array of size 3 with x and y positions at indexes 0 and 1 respectively and index 3 being length.
     */
    private int[] getLaserTransform(){
        double angle = Math.toRadians(360-getRotation());
        int length = 0;

        boolean lookingRight = angle >= 0 && angle <= Math.PI / 2 || angle >= 3 * Math.PI / 2;
        boolean lookingUp = angle <= Math.PI;

        //Set the distances to the walls in the directions they are facing.
        int yDiff = lookingUp ? getY() : 1080 - getY();
        int xDiff = lookingRight ? 1920 - getX() : getX();

        if(Math.abs(xDiff / Math.cos(angle)) < Math.abs(yDiff / Math.sin(angle))){
            length = (int)(xDiff / Math.cos(angle));
        }
        else{
            length = (int)(yDiff / Math.sin(angle));
        }

        int yPositionDiff = (int)(Math.abs(length / 2.0 * Math.sin(angle)));
        int finalY = lookingUp ? getY() - yPositionDiff : getY() + yPositionDiff;

        int xPositionDiff = (int)(Math.abs(length / 2.0 - (length / 2.0 * Math.abs(Math.cos(angle)))));

        length = Math.abs(length) + 100;

        int finalX = lookingRight ? getX() - xPositionDiff + getWidth() : getX() - xPositionDiff - Math.abs((int)(length * Math.cos(angle)));

        return new int[]{finalX, finalY, length};
    }

    /**
     * Finds the location slightly ahead of the shooting entity so the bullet doesn't spawn inside them.
     * @return An int array of size 2 with x and y positions at indexes 0 and 1 respectively.
     */

    private int[] getBulletSpawnLocation(){
        int x = getCenterX();
        int y = getCenterY();
        double angle = Math.toRadians(-getRotation());

        double yDist = distance * Math.sin(angle);
        double xDist = distance * Math.cos(angle);

        return new int[]{(int)(x + xDist), (int)(y - yDist)};
    }
}
