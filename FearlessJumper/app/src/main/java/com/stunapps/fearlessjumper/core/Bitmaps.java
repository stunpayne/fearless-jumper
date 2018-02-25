package com.stunapps.fearlessjumper.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

import static com.stunapps.fearlessjumper.helper.Environment.scaleX;
import static com.stunapps.fearlessjumper.helper.Environment.scaleY;

/**
 * Created by sunny.s on 25/02/18.
 */

public class Bitmaps
{
	public static Bitmap PLATFORM;

	public static void initialise()
	{
		PLATFORM = initBitmap(R.drawable.platform);
	}

	private static Bitmap initBitmap(int drawableResId)
	{
		Bitmap bitmap =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), drawableResId);
		return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scaleX() *
						(Environment.UNIT_DENSITY / Device.DISPLAY_DENSITY)),
				(int) (bitmap.getHeight() * scaleY() *
						(Environment.UNIT_DENSITY / Device.DISPLAY_DENSITY)), false);
	}
}
