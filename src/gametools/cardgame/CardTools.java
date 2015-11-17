package gametools.cardgame;

import gametools.Tools;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CardTools {
    public static enum Directory {
        SMALL_CARDS("smallimg", "jpg"), LARGE_CARDS("largeimg", "png");
        
        private final String path, ext;
        
        private Directory(String path, String ext) {
            this.path = path;
            this.ext = ext;
        }
        
        private BufferedImage loadImage(String name) throws Exception {
            String fullPath = path + "\\" + name + "." + ext;
            return ImageIO.read(CardTools.class.getResourceAsStream(fullPath));
        }
    }
    private static final BufferedImage[][] CARD_IMAGES = new BufferedImage[4][13];
    
    public static void load(Directory type) {
        try {
            for (int s = 0; s < 4; s++)
                for (int v = 0; v < 13; v++)
                    CARD_IMAGES[s][v] = type.loadImage(s + "-" + (v + 1));
            Card.BACK_BLUE = type.loadImage("blue-back");
            Card.BACK_RED = type.loadImage("red-back");
            Card.JOKER_BLACK.setImage(type.loadImage("black-joker"));
            Card.JOKER_RED.setImage(type.loadImage("red-joker"));
        }
        catch (Exception ex) {
            for (int s = 0; s < 5; s++)
                for (int v = 0; v < 13; v++)
                    CARD_IMAGES[s][v] = Tools.UNDEFINED_IMAGE;
            System.err.println("There were errors loading card images:");
            System.err.println(ex.toString());
        }
    }
    
    public static BufferedImage getImage(int suit, int value) {
        return CARD_IMAGES[suit][value - 1];
    }
}
