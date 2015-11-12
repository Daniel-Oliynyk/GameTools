package gametools.cardgame;

import gametools.Tools;
import java.awt.image.BufferedImage;

public class Card {
    public static final BufferedImage[][] ALL_CARD_IMAGES = new BufferedImage[5][13];
    public static enum ImageType {
        SMALL_IMAGES("smallimg/"), LARGE_IMAGES("largeimg/");
        
        private final String path;
        
        private ImageType(String path) {
            this.path = path;
        }
        
        private String path() {
            return path;
        }
    }
    BufferedImage card;
    
    public static void initialize(ImageType type) {
        Class prev = Tools.getRoot();
        Tools.initialize(Card.class);
        for (int s = 0; s < 10; s++)
            for (int v = 0; v < 13; v++)
                ALL_CARD_IMAGES[s][v] = Tools.loadImage(type.path() + s + "-" + v + ".png");
//        ALL_CARD_IMAGES[5][0]
        Tools.initialize(prev);
    }
    
    public Card(int suit, int value) {
        
    }
}
