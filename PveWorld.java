import mayflower.*;

/**
 * The world where players work together to beat the boss.
 */
public class PveWorld extends World
{
    private ShootingActor player1;
    private ShootingActor player2;
    private Boss boss;
    private boolean added;
    /**
     * Constructor for objects of class PveWorld
     */
    public PveWorld()
    {
        MyMayflower.pvp = false;
        MayflowerImage image = new MayflowerImage("img/BG/Space/Green Nebula/2.png");
        image.scale(1920, 1080);

        setBackground(image);

        //Add outer walls
        addObject(new Block(1920, 20), 0, 1060);
        addObject(new Block(1920, 20), 0, 0);
        addObject(new Block(20, 1080), 0, 0);
        addObject(new Block(20, 1080), 1900, 0);
        //left center
        addObject(new UnbreakableBlock(), 410, 440);
        addObject(new UnbreakableBlock(), 470, 440);
        addObject(new UnbreakableBlock(), 410, 500);
        addObject(new UnbreakableBlock(), 470, 500);
        addObject(new UnbreakableBlock(), 530, 440);
        addObject(new UnbreakableBlock(), 530, 560);
        addObject(new UnbreakableBlock(), 530, 500);
        addObject(new UnbreakableBlock(), 410, 560);
        addObject(new UnbreakableBlock(), 470, 560);
        //right center
        addObject(new UnbreakableBlock(), 1310, 440);
        addObject(new UnbreakableBlock(), 1370, 440);
        addObject(new UnbreakableBlock(), 1310, 500);
        addObject(new UnbreakableBlock(), 1370, 500);
        addObject(new UnbreakableBlock(), 1310, 560);
        addObject(new UnbreakableBlock(), 1370, 560);
        addObject(new UnbreakableBlock(), 1430, 500);
        addObject(new UnbreakableBlock(), 1430, 440);
        addObject(new UnbreakableBlock(), 1430, 560);
        //Player
        player1 = new ShootingActor(1);
        addObject(player1, 800, 800);
        addObject(new HealthBar(player1, true), 800, 810);
        addObject(new HealthBar(player1, false), 800, 810);
        addObject(new PlayerTag(1, player1), 800, 830);
        
        player2 = new ShootingActor(2);
        addObject(player2, 800, 800);
        addObject(new HealthBar(player2, true), 800, 810);
        addObject(new HealthBar(player2, false), 800, 810);
        addObject(new PlayerTag(2, player2), 800, 830);
    
        //Boss
        boss = new Boss(player1, player2);
        addObject(boss, 800, 800);
        addObject(new BossHealthBar(1, boss), 600, 0);
        addObject(new BossHealthBar(2, boss), 600, 0);
        addObject(new BossHealthBar(3, boss), 600, 0);
        
        //Consumable Test

    }
    @Override
    public void act()
    {
        lost();
        phase2();
    }
    /**
     * Triggers the lost screen
     */
    public void lost()
    {
        if(player1.dead && player2.dead)
            Mayflower.setWorld(new LostScreen());
    }
    /**
     * Triggers the second phase of the map
     */
    public void phase2()
    {
        //activates when the boss is in its second phase
        if(boss.enraged & !added)
        {
            added = true;
            //adding a solar system
            Star star = new Star();
            addObject(star, 960, 540);
            OrbitingObjects planet = new OrbitingObjects(star, 0, "planet");
            addObject(planet, 0, 0);
            addObject(new OrbitingObjects(planet, 120, "moon"), 0, 0);
            //consumables
            addObject(new Consumable(ConsumableType.TripleShot), 200, 100);
            addObject(new Consumable(ConsumableType.Laser), 1720, 100);
            addObject(new Consumable(ConsumableType.ContinuousShot), 200, 900);
            addObject(new Consumable(ConsumableType.Mine), 1720, 900);
        }
    }
}
