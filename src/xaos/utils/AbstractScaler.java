package xaos.utils;

import java.lang.reflect.Field;

public abstract class AbstractScaler {

    // Defaults
    protected static final float DEFAULT_MIN_FONT_SCALE = 1.0f;
    protected static final float DEFAULT_MAX_FONT_SCALE = 3.0f;
    protected static final float DEFAULT_INITIAL_FONT_SCALE = 1.0f;
    protected static final int DEFAULT_SCALE_STEPS = 12; // must be > 1

    // "Over-rideable" by defining in sub-class
    protected static float MIN_FONT_SCALE = DEFAULT_MIN_FONT_SCALE;
    protected static float MAX_FONT_SCALE = DEFAULT_MAX_FONT_SCALE;
    protected static float INITIAL_FONT_SCALE = DEFAULT_INITIAL_FONT_SCALE;
    protected static int SCALE_STEPS = DEFAULT_SCALE_STEPS; // must be > 1

    // Actually used - per sub-class, per-instance
    protected float MinScale = MIN_FONT_SCALE;
    protected float MaxScale = MAX_FONT_SCALE;
    protected float InitialScale = INITIAL_FONT_SCALE;
    protected int ScaleSteps = SCALE_STEPS;

    private volatile float[] SCALE_VALUES = new float[SCALE_STEPS];
    private transient float STEP_SKIP_INTERVAL = (MAX_FONT_SCALE - MIN_FONT_SCALE) / (float) (SCALE_STEPS - 1);
    
    private volatile int ScaleIndex = -1;
    private volatile float scale = -1.0f;

	private static transient float fClampedVal = 0f;
//    protected static AbstractScaler singleton;
    
        private class NullPassedToScalerException extends NullPointerException {
/*            NullPassedToScalerException() {
                super();
            }
*/            NullPassedToScalerException(final String s) {
                super(s);
            }
/*            NullPassedToScalerException(final Exception e) {
                this();
                this.initCause(e);
            }
            NullPassedToScalerException(final String s, final Exception e) {
                this(s);
                this.initCause(e);
            }
*/        }

          private class NullCannotHappenException extends NullPointerException {
            NullCannotHappenException() {
                this("Impossible situation!  " + 
                          "There should be no way to reach this line of code.");
            }
            NullCannotHappenException(final String s) {
                super(s);
            }
            NullCannotHappenException(final Exception e) {
                this();
                this.initCause(e);
            }
/*            NullCannotHappenException(final String s, final Exception e) {
                super(s);
                this.initCause(e);
            }
*/        }
  
    protected AbstractScaler() {
       init();
    }
  
    protected AbstractScaler(final Class<? extends AbstractScaler> tc) throws
        NullPointerException, ExceptionInInitializerError  {
        init(tc);
    }

    protected AbstractScaler(final float minfontscale, final float maxfontscale
                , final float initfontscale, final  int scalesteps) {
        init(minfontscale, maxfontscale, initfontscale, scalesteps);
    }
 
    private void init() {
        init(MIN_FONT_SCALE, MAX_FONT_SCALE, INITIAL_FONT_SCALE, SCALE_STEPS);
    }

    protected void init(Class<? extends AbstractScaler> tc) throws NullPassedToScalerException,
        NullPointerException, ExceptionInInitializerError  {
/*
        if (t == null) {
            throw new NullPassedToScalerException("AbstractScaler class constructor called with null parameter!");
        }

        // debug
        System.out.println("AbstractScaler constructor called from " + t.getClass().getCanonicalName());
        Field minfs = null, maxfs = null, initfs = null, ss = null;

        Class<? extends AbstractScaler> tc = t.getClass();
*/
        if (tc == null) {
            throw new NullPassedToScalerException("AbstractScaler class constructor called with object having a null class?!");
        }

        // debug
        System.out.println("AbstractScaler constructor called from " + tc.getCanonicalName());

        Field minfs = null, maxfs = null, initfs = null, ss = null;

        try {
            minfs = tc.getDeclaredField("MIN_FONT_SCALE");
        } catch (NoSuchFieldException e) {
            // continue - this is OK
        } catch (NullPointerException e) {
            // Should not be possible...!
            throw new NullCannotHappenException(e);
        }

        try {
            maxfs = tc.getDeclaredField("MAX_FONT_SCALE");
       } catch (NoSuchFieldException e) {
            // continue - this is OK
        } catch (NullPointerException e) {
            // Should not be possible...!
            throw new NullCannotHappenException(e);
        }

        try {
            initfs = tc.getDeclaredField("INITIAL_FONT_SCALE");
        } catch (NoSuchFieldException e) {
            // continue - this is OK
        } catch (NullPointerException e) {
            // Should not be possible...!
            throw new NullCannotHappenException(e);
        }

        try {
            ss = tc.getDeclaredField("SCALE_STEPS");
        } catch (NoSuchFieldException e) {
            // continue - this is OK
        } catch (NullPointerException e) {
            // Should not be possible...!
            throw new NullCannotHappenException(e);
        }

        if (minfs != null) {
            try {
                MIN_FONT_SCALE = minfs.getFloat(tc);
            } catch (IllegalAccessException e) {
                // log warning and continue
            } catch (IllegalArgumentException e) {
                // log warning and continue
            } catch (NullPointerException e) {
            throw new NullCannotHappenException(e);
            } catch (ExceptionInInitializerError e) {
                // pass it along
                throw e;
            }            
        }

        if (maxfs != null) {
            try {
                MIN_FONT_SCALE = minfs.getFloat(tc);
            } catch (IllegalAccessException e) {
                // log warning and continue
            } catch (IllegalArgumentException e) {
                // log warning and continue
            } catch (NullPointerException e) {
            throw new NullCannotHappenException(e);
            } catch (ExceptionInInitializerError e) {
                // pass it along
                throw e;
            }
        }

        if (initfs != null) {
            try {
                INITIAL_FONT_SCALE = initfs.getFloat(tc);
            } catch (IllegalAccessException e) {
                // log warning and continue
            } catch (IllegalArgumentException e) {
                // log warning and continue
            } catch (NullPointerException e) {
            throw new NullCannotHappenException(e);
            } catch (ExceptionInInitializerError e) {
                // pass it along
                throw e;
            }
        }

        if (ss != null) {
            try {
                SCALE_STEPS = ss.getInt(tc);
            } catch (IllegalAccessException e) {
                // log warning and continue
            } catch (IllegalArgumentException e) {
                // log warning and continue
            } catch (NullPointerException e) {
            throw new NullCannotHappenException(e);
            } catch (ExceptionInInitializerError e) {
                // pass it along
                throw e;
            }
        }

        finishInit();
/*        
        if (singleton == null) {
            singleton = t;
        }
*/    }

    private void init(float minfontscale, float maxfontscale, float initfontscale, int scalesteps) {
        MIN_FONT_SCALE = minfontscale;
        MAX_FONT_SCALE = maxfontscale;
        INITIAL_FONT_SCALE = initfontscale;
        SCALE_STEPS = scalesteps;

        finishInit();
    }

    private void finishInit() {
/*       
        //debug
        new Throwable().fillInStackTrace().printStackTrace();
*/
        MinScale = MIN_FONT_SCALE;
        MaxScale = MAX_FONT_SCALE;
        InitialScale = INITIAL_FONT_SCALE;
        ScaleSteps = SCALE_STEPS;

        fillScaleArray();
        ScaleIndex = getClosestIndex(InitialScale);
        scale = SCALE_VALUES[ScaleIndex];
    }
/*
    public static final synchronized AbstractScaler getInstance() {
        if (singleton == null) {
            singleton = this; new AbstractScaler();
        }
        return singleton;
    }
*/
    public final synchronized float get() { // synchronized probably not needed here...
        return scale;
    }

    public final synchronized void set(final float newScale) {
        scale = clamp(newScale);
    }

    public final synchronized float clamp(final float fVal) {
        fClampedVal = (Float.isFinite(fVal)) ? fVal : INITIAL_FONT_SCALE;
        fClampedVal = Math.min(Math.max(fClampedVal, MIN_FONT_SCALE), MAX_FONT_SCALE);
        return fClampedVal;
    }

    protected boolean fillScaleArray() {
        SCALE_VALUES[0] = MinScale;
        float s = MinScale;
        for (int i = 1; i < (ScaleSteps - 1); i++) {
            s += STEP_SKIP_INTERVAL;
            SCALE_VALUES[i] = s;
        }
        SCALE_VALUES[ScaleSteps - 1] = MaxScale;

        // Debug
        {
            for (int i = 0; i < ScaleSteps; i++) {
                System.out.println("scale array[" + i + "] = " + Float.toString(SCALE_VALUES[i]));
            }
//            new Throwable().fillInStackTrace().printStackTrace();
        }

        return true;
    }

    public final  synchronized short px(final int value) {
        return (short) (((float) value) * scale);
    }

    public final synchronized int textWidth(final String text) {
        return (int) (((float) (UtilFont.getWidth(text))) * scale);
    }

    public final int fontWidth() {
        return px(UtilFont.MAX_WIDTH);
    }

    public final int fontWidth(final float inScaleOverride) {
        return ((inScaleOverride <= 0f)
                    ?fontWidth()
                    :(int)(((float) UtilFont.MAX_WIDTH) * clamp(inScaleOverride)));
    }

    public final int fontHeight() {
        return px(UtilFont.MAX_HEIGHT);
    }

    public final int fontHeight(final float inScaleOverride) {
        return ((inScaleOverride <= 0f)
                    ?fontHeight()
                    :(int)(((float) UtilFont.MAX_HEIGHT) * clamp(inScaleOverride)));
    }

    
    public void cycleScale() {
		ScaleIndex++;

		if (ScaleIndex >= SCALE_VALUES.length) {
			ScaleIndex = 0;
		}

		scale = SCALE_VALUES[ScaleIndex];
	}

    public String getDisplayText() {
		return Math.round(scale * 100f) + "%";
	}

	protected final int getClosestIndex(float value) {
		int closestIndex = 0;
		float closestDistance = Math.abs(SCALE_VALUES[0] - value);

		for (int i = 1; i < SCALE_VALUES.length; i++) {
			float distance = Math.abs(SCALE_VALUES[i] - value);

			if (distance < closestDistance) {
				closestDistance = distance;
				closestIndex = i;
			}
		}

		return closestIndex;
	}

    public final CharDef getScaledCharDef (final int charID) {
		CharDef cd = UtilFont.getCharDef(charID);
        cd.x = px(cd.x);
        cd.y = px(cd.y);
        cd.width = px(cd.width);
        cd.height = px(cd.height);
        cd.xadvance = px(cd.xadvance);
        cd.yoffset = px(cd.yoffset);

        return cd;
    }

}