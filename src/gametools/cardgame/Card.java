package gametools.cardgame;

import gametools.Graphic;
import gametools.Position;
import gametools.Tools;
import java.awt.image.BufferedImage;

public class Card extends Graphic {
    public static final Card UNDEFINED_CARD = new Card(-1, -1),
            JOKER_BLACK = new Card(-1, 14), JOKER_RED = new Card(-2, 14);
    public static BufferedImage BACK_BLUE = Tools.UNDEFINED_IMAGE,
            BACK_RED = Tools.UNDEFINED_IMAGE;
    public static final class Value {
        private Value() {}
        public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13, JOKER = 14;
    }
    public static final class Suit {
        private Suit() {}
        public static final int SPADE = 0, HEART = 1, DIAMOND = 2, CLUB = 3; 
    }
    
    private int suit, value;
    private boolean flipped;
    
    public Card(Position pos, int suit, int value) {
        this(suit, value);
        setPosition(pos);
    }
    
    public Card(int suit, int value) {
        if (suit == -1 && value == -1) {
            setImage(Tools.UNDEFINED_IMAGE);
        }
        else if (value == Value.JOKER) setImage(Tools.UNDEFINED_IMAGE);
        else {
            boolean validSuit = suit >= 0 && suit < 4;
            boolean validValue = value >= 1 && suit < Deck.Type.ALL_CARDS.limit();
            if (validSuit && validValue) setImage(CardTools.getImage(suit, value));
            else {
                System.err.println("An invalid card with a suit of " + suit +
                        " and a value of " + value + " was attempted to be created.");
                setImage(Tools.UNDEFINED_IMAGE);
            }
        }
        this.suit = suit;
        this.value = value;
    }
    
    public void setSuit(int suit) {
        setCard(suit, value);
    }
    
    public void setValue(int value) {
        setCard(suit, value);
    }
    
    public void setCard(Card card) {
        setCard(card.suit, card.value);
    }
    
    public void setCard(int suit, int value) {
        setImage(CardTools.getImage(suit, value));
        this.suit = suit;
        this.value = value;
    }
    
    public int getSuit() {
        return suit;
    }
    
    public int getValue() {
        return value;
    }
    
    public void flip() {
        if (flipped) setImage(CardTools.getImage(suit, value));
        else setImage(BACK_BLUE);
        flipped = !flipped;
    }
}
