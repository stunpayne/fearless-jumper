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
	public static SharedPreferences SHARED_PREFERENCES;

	public static float BASE_DENSITY = 2.625f;
	public static float BASE_HEIGHT = 1794f;
	public static float BASE_WIDTH = 1080f;

	private static float UNIT_X = 54f;
	private static float UNIT_Y = 51f;

	public class Settings
	{
		public static final boolean DRAW_COLLIDERS = false;
		public static final boolean DEBUG_MODE = false;
		public static final boolean LOG_GRAPH = false;
		public static final boolean PRINT_FPS = true;
		public static final boolean PRINT_SENSOR_DATA = false;
		public static final boolean ACTUAL_SENSOR_ROTATION = true;
	}

	public static float scaleY()
	{
		return Device.SCREEN_HEIGHT / BASE_HEIGHT;
	}

	public static float scaleX()
	{
		return Device.SCREEN_WIDTH / BASE_WIDTH;
	}

	public static float scaleY(float value)
	{
		return value * scaleY();
	}

	public static float scaleX(float value)
	{
		return value * scaleX();
	}

	public static float unitX()
	{
		return scaleX(UNIT_X);
	}

	public static float unitY()
	{
		return scaleX(UNIT_Y);
	}
}
