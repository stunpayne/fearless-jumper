package com.stunapps.fearlessjumper.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.AudioManager;


/**
 * Created by sunny.s on 10/01/18.
 */

public class Environment
{
	public static class Device
	{
		public static int SCREEN_WIDTH;
		public static int SCREEN_HEIGHT;
		public static float DISPLAY_DENSITY;
		public static AudioManager audioManager;
	}

	public static Context CONTEXT;

	public static Canvas CANVAS;

	public static float UNIT_DENSITY = 2.625f;
	public static float UNIT_HEIGHT = 1794f;
	public static float UNIT_WIDTH = 1080f;

	public static SharedPreferences SHARED_PREFERENCES;

	public class Settings
	{
		public static final boolean DRAW_COLLIDERS = false;
		public static final boolean DEBUG_MODE = false;
		public static final boolean LOG_GRAPH = false;
		public static final boolean PRINT_FPS = true;
		public static final boolean PRINT_SENSOR_DATA = false;
	}

	public static float scaleY()
	{
		return Device.SCREEN_HEIGHT / UNIT_HEIGHT;
	}

	public static float scaleX()
	{
		return Device.SCREEN_WIDTH / UNIT_WIDTH;
	}

	public static float scaleY(float value)
	{
		return value * scaleY();
	}

	public static float scaleX(float value)
	{
		return value * scaleX();
	}
}
