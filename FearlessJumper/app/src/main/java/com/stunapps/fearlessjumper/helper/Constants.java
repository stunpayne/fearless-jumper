package com.stunapps.fearlessjumper.helper;

import android.content.Context;
import android.graphics.Canvas;

import com.stunapps.fearlessjumper.component.transform.Transform;


/**
 * Created by sunny.s on 10/01/18.
 */

public class Constants
{
    //  Copying these values for now. Need to check if there is a better way to implement this
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float UNIT_DENSITY = 2.625f;
    public static float DISPLAY_DENSITY;

    public static Context CURRENT_CONTEXT;

    public static long INIT_TIME;
    public static Canvas canvas;

    public static class Game
    {
        public static Transform.Position ORIGIN = new Transform.Position();
        public static Transform.Rotation NO_ROTATION = new Transform.Rotation();
        public static Transform.Scale UNIT_SCALE = new Transform.Scale();
    }

    public static float scale()
    {
        return DISPLAY_DENSITY / UNIT_DENSITY;
    }
}
