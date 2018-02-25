package com.stunapps.fearlessjumper.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;

import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;


/**
 * Created by sunny.s on 10/01/18.
 */

public class Environment
{
    //  Copying these values for now. Need to check if there is a better way to implement this
    public static class Device
    {
        public static int SCREEN_WIDTH;
        public static int SCREEN_HEIGHT;
        public static float DISPLAY_DENSITY;
    }
    public static Context CONTEXT;

    public static Activity ACTIVITY;
    public static long INIT_TIME;

    public static Canvas CANVAS;

    public static float UNIT_DENSITY = 2.625f;
    public static float UNIT_HEIGHT = 1794f;
    public static float UNIT_WIDTH = 1080f;

    public class Constants
    {
        public static final long ONE_MILLION = 1000000;
        public static final long ONE_SECOND_NANOS = 1000000000;
    }

    public static float scaleY()
    {
        return Device.SCREEN_HEIGHT / UNIT_HEIGHT;
    }

    public static float scaleX()
    {
        return Device.SCREEN_WIDTH / UNIT_WIDTH;
    }
}
