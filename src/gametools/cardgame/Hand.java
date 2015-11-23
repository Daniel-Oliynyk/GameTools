package gametools.cardgame;

import gametools.Graphic;
import gametools.Position;
import gametools.Tools;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Hand extends Graphic {
    public static enum Sort {
        VALUE, VALUE_DESC, SUIT, SUIT_DESC;
    }
    private final List<Card> hand = new ArrayList<>();
    private Position offset = new Position(0, 30);
    
    public Hand() {
        setImage(Tools.UNDEFINED_IMAGE);
    }
    
    public Card get(int i) {
        return hand.get(i);
    }
    
    public int size() {
        return hand.size();
    }
    
    public void sort(Sort sort) {
        if (sort == Sort.VALUE)
            hand.sort((Card t1, Card t2) -> t1.getValue() - t2.getValue());
        else if (sort == Sort.VALUE_DESC)
            hand.sort((Card t1, Card t2) -> t2.getValue() - t1.getValue());
        else if (sort == Sort.SUIT)
            hand.sort((Card t1, Card t2) -> t1.getSuit() - t2.getSuit());
        else if (sort == Sort.SUIT_DESC)
            hand.sort((Card t1, Card t2) -> t2.getSuit() - t1.getSuit());
        fixImage();
        //Finish here
    }
    
    public void setOffsetX(double offset) {
        setOffset(new Position(offset, this.offset.y()));
    }
    
    public void setOffsetY(double offset) {
        setOffset(new Position(this.offset.x(), offset));
    }
    
    public void setOffset(Position offset) {
        this.offset = offset;
        fixImage();
    }
    
    public void addAll(List<Card> cards) {
        hand.addAll(cards);
        fixImage();
    }
    
    protected void fixImage() {
        if (hand.size() < 1) setImage(Tools.UNDEFINED_IMAGE);
        else if (hand.size() == 1) setImage(hand.get(0).getImage());
        else {
            int size = hand.size() - 1;
            int width = (int) (hand.get(0).getWidth() + size * offset.x());
            int height = (int) (hand.get(0).getHeight() + size * offset.y());
            BufferedImage all = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = all.createGraphics();
            for (int i = 0; i < hand.size(); i++) {
                int offsetX = (int) (i * offset.x());
                int offsetY = (int) (i * offset.y());
                graphics.drawImage(hand.get(i).getImage(), offsetX, offsetY, null);
            }
            setImage(all);
        }
    }
    
    public void add(Card card) {
        hand.add(card);
        fixImage();
    }
    
    public int getTotal() {
        int total = 0;
        for (Card card : hand) total += card.getValue();
        return total;
    }
}
