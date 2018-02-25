package com.stunapps.fearlessjumper.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

import lombok.Getter;

import static com.stunapps.fearlessjumper.helper.Environment.scaleX;
import static com.stunapps.fearlessjumper.helper.Environment.scaleY;

/**
 * Created by sunny.s on 25/02/18.
 */

public class Bitmaps
{
	//	Player bitmaps
	public static Bitmap PLAYER_IDLE;
	public static Bitmap PLAYER_HURT;
	public static Bitmap PLAYER_DIED;

	//	Obstacles
	public static Bitmap PLATFORM;
	public static Bitmap CLOCK;

	//	Dragon
	public static Bitmap DRAGON_FLY1;
	public static Bitmap DRAGON_FLY2;
	public static Bitmap DRAGON_FLY3;
	public static Bitmap DRAGON_FLY4;

	public static Bitmap BLANK_IMAGE;

	public static void initialise()
	{
		PLAYER_IDLE = initBitmap(R.drawable.alienblue, 90, 125);
		PLAYER_HURT = initBitmap(R.drawable.alienblue_hurt, 90, 125);
		PLAYER_DIED = initBitmap(R.drawable.alienblue_died, 90, 125);

		PLATFORM = initBitmap(R.drawable.platform, 400, 100);
		CLOCK = initBitmap(R.drawable.clock, 115, 115);

		DRAGON_FLY1 = initBitmap(R.drawable.dragon_fly1, Dimensions.dragonX, Dimensions.dragonY);
		DRAGON_FLY2 = initBitmap(R.drawable.dragon_fly2, Dimensions.dragonX, Dimensions.dragonY);
		DRAGON_FLY3 = initBitmap(R.drawable.dragon_fly3, Dimensions.dragonX, Dimensions.dragonY);
		DRAGON_FLY4 = initBitmap(R.drawable.dragon_fly4, Dimensions.dragonX, Dimensions.dragonY);

		BLANK_IMAGE = initBitmap(R.drawable.blank_image, 90, 125);
	}

	private static Bitmap initBitmap(int drawableResId, int width, int height)
	{
		Bitmap bitmap =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), drawableResId);
		return Bitmap
				.createScaledBitmap(bitmap, (int) (width * scaleX()), (int) (height * scaleY()),
						false);
	}

	@Getter
	private static class Dimensions
	{
		private static int playerX = 90;
		private static int playerY = 125;

		private static int dragonX = 160;
		private static int dragonY = 115;
	}
}
