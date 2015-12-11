package gametools;

public abstract class Script {
    public static final Script UNDEFINED_SCRIPT = new Script(new Sprite()) {
        @Override
        public void update() {}
    };
    
    private final Sprite sprite;

    public Script(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public Sprite sprite() {
        return sprite;
    }
    
    public abstract void update();
}
