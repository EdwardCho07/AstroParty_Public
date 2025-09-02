import mayflower.Actor;
import mayflower.Mayflower;
import mayflower.MayflowerImage;

/**
 * A button that can be clicked on to call a function
 */
public class Button extends Actor {
    private int height;
    private int width;
    private String id;

    private MayflowerImage normalImage;
    private MayflowerImage hoverImage;

    public Button(int _height, int _width, String _id){
        height = _height;
        width = _width;
        id = _id;

        switch (id){
            case "startselection":
                normalImage = new MayflowerImage("img/BG/Buttons/Start.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/StartHighlighted.png");
                break;
            case "startpvp":
                normalImage = new MayflowerImage("img/BG/Buttons/PVP.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/PVPHighlighted.png");
                break;
            case "startpve":
                normalImage = new MayflowerImage("img/BG/Buttons/PVE.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/PVEHighlighted.png");
                break;
            case "menu1":
                normalImage = new MayflowerImage("img/BG/Buttons/menu1.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/menu1Highlighted.png");
                break;
            case "menu2":
                normalImage = new MayflowerImage("img/BG/Buttons/menu2.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/menu2Highlighted.png");  
                break;
            case "menu3":
                normalImage = new MayflowerImage("img/BG/Buttons/menuPve.png");
                hoverImage = new MayflowerImage("img/BG/Buttons/menuPveHighlighted.png");  
                break;
            default:
                normalImage = new MayflowerImage("img/Tiles/tempborder.png");
                hoverImage = new MayflowerImage("img/Tiles/tempborder.png");
                break;
        }

        normalImage.scale(width, height);
        hoverImage.scale(width, height);
        setImage(normalImage);
    }

    @Override
    public void act() {
        if(Mayflower.mouseHovered(this)){
            setImage(hoverImage);
        }
        else{
            setImage(normalImage);
        }

        if(Mayflower.mouseClicked(this)){
            switch (id){
                case "startselection":
                    Mayflower.setWorld(new SelectionScreen());
                    break;
                case "startpvp":
                    Mayflower.setWorld(MyMayflower.getRandomPvpWorld());
                    break;
                case "startpve":
                    Mayflower.setWorld(new PveWorld());
                    break;
                case "menu1":
                    Mayflower.setWorld(new MainMenu());
                    break;
                case "menu2":
                    Mayflower.setWorld(new MainMenu());
                    break;
                case "menu3":
                    Mayflower.setWorld(new MainMenu());
                    break;
            }
        }
    }
}
