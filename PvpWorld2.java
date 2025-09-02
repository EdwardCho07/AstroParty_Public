import mayflower.Actor;
import mayflower.Mayflower;
import mayflower.MayflowerImage;
import mayflower.World;

import java.util.Timer;
import java.util.TimerTask;

/**
 * One of the pvp worlds characterized by random lasers that pop up.
 */
public class PvpWorld2 extends World {
    private Timer laserTimer;
    private Timer timer;

    public PvpWorld2(){
        MyMayflower.pvp = true;
        MayflowerImage image = new MayflowerImage("img/BG/Space/Purple Nebula/3.png");
        image.scale(1920, 1080);

        laserTimer = new Timer();
        timer = new Timer();

        setBackground(image);

        buildWorld();

        laserTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SpawnLaser();
            }
        }, 10000, 10000);
    }

    /**
     * Includes all the code to build the world. Called from the constructor.
     */
    private void buildWorld(){
        //Add outer walls
        addObject(new Block(1920, 20), 0, 1060);
        addObject(new Block(1920, 20), 0, 0);
        addObject(new Block(20, 1080), 0, 0);
        addObject(new Block(20, 1080), 1900, 0);

        //Player
        ShootingActor player1 = new ShootingActor(1);
        addObject(player1, 80, 1000);
        addObject(new HealthBar(player1, true), 800, 810);
        addObject(new HealthBar(player1, false), 800, 810);
        addObject(new PlayerTag(1, player1), 800, 830);

        ShootingActor player2 = new ShootingActor(2);
        addObject(player2, 1860, 40);
        addObject(new HealthBar(player2, true), 800, 810);
        addObject(new HealthBar(player2, false), 800, 810);
        addObject(new PlayerTag(2, player2), 800, 830);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addRandomConsumable(9, 16);
            }
        }, 20000, 20000);
    }

    /**
     * Gives the warning for and then spawns one or more of the four lasers randomly.
     */
    private void SpawnLaser(){
        boolean[] spawnLaser = new boolean[4];
        for(int i = 0; i < spawnLaser.length; i++){
            if(Math.random() < 0.33){
                spawnLaser[i] = true;
            }
        }

        if(spawnLaser[0]){
            addObject(new WarningSign(3000), 610, 60);
            laserTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    addObject(new Laser(90, 1500, null, 1, 80), -110, 540);
                }
            }, 3000);
        }

        if(spawnLaser[1]){
            addObject(new WarningSign(3000), 1250, 60);
            laserTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    addObject(new Laser(90, 1500, null, 1, 80), 530, 540);
                }
            }, 3000);
        }

        if(spawnLaser[2]){
            addObject(new WarningSign(3000), 0, 360);
            laserTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    addObject(new Laser(0, 2500, null, 1, 80), -400, 360);
                }
            }, 3000);
        }

        if(spawnLaser[3]){
            addObject(new WarningSign(3000), 0, 720);
            laserTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    addObject(new Laser(0, 2500, null, 1, 80), -400, 720);
                }
            }, 3000);
        }
    }

    /**
     * Creates an Unbreakable block at the given row and column. 32 columns and 18 rows. Bottom left is 0, 0.
     * @param r The row number for the block. 0-17
     * @param c The column number for the block. 0-31
     */
    public void addBlock(int r, int c){
        addObject(new UnbreakableBlock(), c * 60, 1020 - (r * 60));
    }

    /**
     * Creates a Breakable block at the given row and column. 32 columns and 18 rows. Bottom left is 0, 0.
     * @param r The row number for the block. 0-17
     * @param c The column number for the block. 0-31
     */
    public void addBreakableBlock(int r, int c){
        addObject(new BreakableBlock(), c * 60, 1020 - (r * 60));
    }

    /**
     * Creates a random Consumable at the given row and column. 32 columns and 18 rows. Bottom left is 0, 0.
     * @param r The row number for the block. 0-17
     * @param c The column number for the block. 0-31
     */
    public void addRandomConsumable(int r, int c){
        Actor a = null;
        ConsumableType randomType = ConsumableType.values()[(int)(Math.random() * ConsumableType.values().length)];
        addObject(new Consumable(randomType), c * 60, 1020 - (r * 60));
    }

    @Override
    public void act() {

    }
}
