import mayflower.Actor;
import mayflower.Mayflower;
import mayflower.MayflowerImage;
import mayflower.World;

import java.util.Timer;
import java.util.TimerTask;

/**
 * One of the pvp worlds characterized by a large amount of breakable boxes.
 */
public class PvpWorld1 extends World {
    private Timer timer;

    public PvpWorld1(){
        MyMayflower.pvp = true;
        MayflowerImage image = new MayflowerImage("img/BG/Space/Blue Nebula/1.png");
        image.scale(1920, 1080);
        timer = new Timer();

        setBackground(image);

        buildWorld();
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

        //Unbreakable Blocks
        addBlock(3, 0);
        addBlock(0, 3);
        addBlock(17, 28);
        addBlock(14, 31);

        //Breakable Blocks
        addBreakableBlock(3, 1);
        addBreakableBlock(3, 2);
        addBreakableBlock(3, 3);
        addBreakableBlock(2, 3);
        addBreakableBlock(1, 3);

        addBreakableBlock(16, 28);
        addBreakableBlock(15, 28);
        addBreakableBlock(14, 28);
        addBreakableBlock(14, 29);
        addBreakableBlock(14, 30);

        for (int i1 = 12; i1 < 20; i1++) {
            addBreakableBlock(5, i1);
        }

        for (int k = 12; k < 20; k++) {
            addBreakableBlock(12, k);
        }

        for (int j = 5; j < 12; j++) {
            addBreakableBlock(j, 12);
        }

        for (int i = 5; i < 12; i++) {
            addBreakableBlock(i, 19);
        }

        for(int i = 0; i < 12; i++){
            addBreakableBlock(8, i);
            addBreakableBlock(9, i);
            addBreakableBlock(8, 20 + i);
            addBreakableBlock(9, 20 + i);
        }

        for (int i = 0; i < 5; i++){
            addBreakableBlock(13 + i, 11 - i);
            addBreakableBlock(4 - i, 11 - i);
            addBreakableBlock(13 + i, 20 + i);
            addBreakableBlock(4 - i, 20 + i);
        }

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
