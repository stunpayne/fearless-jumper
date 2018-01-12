package com.stunapps.fearlessjumper.helper;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by sunny.s on 10/01/18.
 */

public class Constants
{
    //  Copying these values for now. Need to check if there is a better way to implement this
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static Context CURRENT_CONTEXT;

    public static long INIT_TIME;
    public static Canvas canvas;

    public static class Game
    {
        public static float[] ORIGIN = new float[]{0, 0};
        public static float[] NO_ROTATION = new float[]{0, 0};
        public static float[] UNIT_SCALE = new float[]{1, 1};
    }
}
