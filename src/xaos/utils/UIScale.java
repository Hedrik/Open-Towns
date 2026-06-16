package xaos.utils;

public final class UIScale {

    private static final float MIN_FONT_SCALE = 1.0f;
    private static final float MAX_FONT_SCALE = 3.0f;
    private static final float DEFAULT_FONT_SCALE = 1.0f;

    private static float scale = DEFAULT_FONT_SCALE;

    private static transient volatile float fClampedVal = 0f;

    private UIScale() {}

    public static synchronized float get() {
        return scale;
    }

    private static final synchronized float clamp(float fVal) {
        fClampedVal = (!Float.isFinite(fVal)) ? DEFAULT_FONT_SCALE : fVal;
        fClampedVal = Math.min(Math.max(fClampedVal,MIN_FONT_SCALE),MAX_FONT_SCALE);
        return fClampedVal;
    }

    public static synchronized void set(final float newScale) {
        scale = clamp(newScale);
    }

    public static short px(final int value) {
        return (short) (((float) value) * scale);
    }

    public static int textWidth(final String text) {
        return (int) (((float) (UtilFont.getWidth(text))) * scale);
    }

    public static int fontWidth() {
        return px(UtilFont.MAX_WIDTH);
    }

    public static int fontHeight() {
        return px(UtilFont.MAX_HEIGHT);
    }

     public static synchronized int fontHeight(final float inScaleOverride) {
        return ((inScaleOverride == 0f)?fontHeight():(int)(((float) UtilFont.MAX_HEIGHT) * clamp(inScaleOverride)));
    }

/*
    public static CharDef getCharDefScaled (int charID) {
		CharDef cd = UtilFont.getCharDef (int charID);

        return cd;
    }
*/
}