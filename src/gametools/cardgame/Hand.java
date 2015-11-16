package gametools.cardgame;

import gametools.Graphic;
import gametools.Position;
import java.util.ArrayList;
import java.util.List;

public class Hand extends Graphic {
    private final List<Card> hand = new ArrayList<>();

    public Hand() {
        setImage(Card.BACK_BLUE);
    }
    
    public void add(Card card) {
        hand.add(card);
    }
    
    @Override
    public void draw() {
        updateDrag();
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).setDraggable(false);
            hand.get(i).setPosition(new Position(x, y + i * 35));
            hand.get(i).draw();
        }
    }
}
