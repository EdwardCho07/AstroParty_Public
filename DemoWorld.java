import mayflower.Mayflower;
import mayflower.MayflowerImage;
import mayflower.World;

public class DemoWorld extends World {
    public DemoWorld(){
        MyMayflower.pvp = true;
        MayflowerImage image = new MayflowerImage("img/Tiles/whiteborder.jpg");
        image.scale(1920, 1080);

        setBackground(image);
        Mayflower.showBounds(true);

        //Add outer walls
        addObject(new Block(1920, 20), 0, 1060);
        addObject(new Block(1920, 20), 0, 0);
        addObject(new Block(20, 1080), 0, 0);
        addObject(new Block(20, 1080), 1900, 0);

        //Add inner bounding walls for corners
        //Vertical walls
        addObject(new Block(20, 300), 400, 0);
        addObject(new Block(20, 300), 1500, 0);
        addObject(new Block(20, 300), 400, 780);
        addObject(new Block(20, 300), 1500, 780);

        //Horizontal walls
        addObject(new Block(300, 20), 0, 400);
        addObject(new Block(300, 20), 0, 680);
        addObject(new Block(300, 20), 1600, 400);
        addObject(new Block(300, 20), 1600, 680);

        //Middle wall
       //  addObject(new Block(20, 350), 950, 0);
       // addObject(new Block(20, 350), 950, 730);


        //Player
        ShootingActor player1 = new ShootingActor(1);
        addObject(player1, 800, 800);
        addObject(new HealthBar(player1, true), 800, 810);
        addObject(new HealthBar(player1, false), 800, 810);
        addObject(new PlayerTag(1, player1), 800, 830);
        
        ShootingActor player2 = new ShootingActor(2);
        addObject(player2, 800, 800);
        addObject(new HealthBar(player2, true), 800, 810);
        addObject(new HealthBar(player2, false), 800, 810);
        addObject(new PlayerTag(2, player2), 800, 830);
    
        //Boss
        Boss boss = new Boss(player1, player1);
        addObject(boss, 800, 800);
        addObject(new BossHealthBar(1, boss), 600, 0);
        addObject(new BossHealthBar(2, boss), 600, 0);
        addObject(new BossHealthBar(3, boss), 600, 0);
        //Consumable Test
        addObject(new Consumable(ConsumableType.Mine), 500, 500);
    }

    @Override
    public void act() {

    }
}
