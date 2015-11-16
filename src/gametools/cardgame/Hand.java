package gametools.cardgame;

import gametools.Graphic;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Hand extends Graphic {
    private final List<Card> hand = new ArrayList<>();
    
    public Hand() {
        setImage(Card.BACK_BLUE);
    }
    
    public void addAll(List<Card> cards) {
        
    }
    
    public void add(Card card) {
        if (getImage() == Card.BACK_BLUE) setImage(card.getImage());
        else {
            Dimension size = new Dimension(getImage().getWidth(), getImage().getHeight() + 30);
            BufferedImage all = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D draw = all.createGraphics();
            draw.drawImage(getImage(), 0, 0, null);
            draw.drawImage(card.getImage(), 0, all.getHeight() - card.getImage().getHeight(), null);
            setImage(all);
        }
        hand.add(card);
    }
}
