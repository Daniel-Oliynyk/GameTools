package gametools.cardgame;

import gametools.Graphic;
import gametools.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck extends Graphic {
    public static enum Type {
        ALL_CARDS(15), NO_JOKERS(14), NO_FACE_CARDS(11);
        
        private final int limit;
        
        private Type(int limit) {
            this.limit = limit;
        }
        
        public int limit() {
            return limit;
        }
    }
    private final List<Card> deck = new ArrayList<>();
    
    public Deck(Position pos, Type type) {
        this(type);
        setPosition(pos);
    }
    
    public Deck(Type type) {
        super(Card.BACK_BLUE);
        for (int s = 1; s < 4; s++)
            for (int v = 1; v < type.limit(); v++)
                deck.add(new Card(s, v));
        shuffle();
        //Remove extra jokers
        //Contains method
    }
    
    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    public Card peek() {
        return deck.get(0);
    }
    
    public Card deal() {
        Card top = peek();
        remove(top);
        return top;
    }
    
    public boolean contains(int suit, int value) {
        return contains(new Card(suit, value));
    }
    
    public boolean contains(Card card) {
        return deck.contains(card);
    }
    
    public void add(Card card) {
        deck.add(card);
    }
    
    public int size() {
        return deck.size();
    }
    
    public List<Card> getAll() {
        return deck;
    }
    
    public void remove(Card card) {
        deck.remove(card);
    }
    
    public boolean isEmpty() {
        return deck.isEmpty();
    }
}
