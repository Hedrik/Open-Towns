package xaos.panels.UI;

import xaos.utils.AbstractScaler;

public class UIScaler extends AbstractScaler {
    
    static {
        MIN_FONT_SCALE = 0.75f;
        MAX_FONT_SCALE = 1.5f;
        INITIAL_FONT_SCALE = 1.0f;
        SCALE_STEPS = 4;
    }

	public UIScaler() {
        super(UIScaler.class);
    }

    protected UIScaler(final Class<? extends AbstractScaler> tc)
			throws NullPointerException, ExceptionInInitializerError  {
		super(tc);
	}


	public static float getUIScale() {
		return get();
	}

	public static void setUIScale(float scale) {
		set(scale);
	}

	public static void cycleUIScale() {
		cycleScale();
	}

	public static int ui(int value) {
		return px(value);
	}

}