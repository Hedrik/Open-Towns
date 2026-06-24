package xaos.utils;

public final class TooltipScale extends AbstractScaler {
    
    static {
        MIN_FONT_SCALE = 0.75f;
        MAX_FONT_SCALE = 2.0f;
        INITIAL_FONT_SCALE = 1.0f;
        SCALE_STEPS = 6;
    }

    public TooltipScale() {
        super(TooltipScale.class);
    }
   
    public static void cycleTooltipScale() {
        cycleScale();
    }

}