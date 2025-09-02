import mayflower.MayflowerImage;
import mayflower.World;

/**
 * The world where players choose the PvP or PvE mode.
 */
public class SelectionScreen extends World {
    public SelectionScreen(){
        MayflowerImage image = new MayflowerImage("img/BG/SelectionScreen.png");
        image.scale(1920, 1080);

        setBackground(image);

        addObject(new Button(150, 450, "startpvp"), 300, 800);
        addObject(new Button(150, 450, "startpve"), 1155, 800);
    }

    @Override
    public void act() {

    }
}
