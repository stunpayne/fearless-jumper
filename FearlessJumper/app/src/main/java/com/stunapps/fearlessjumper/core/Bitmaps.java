package com.stunapps.fearlessjumper.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.game.Environment;

import lombok.Getter;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

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
	public static Bitmap CLOCK;

	public static Bitmap BLANK_IMAGE;

	//	Textures
	public static Bitmap FIRE_TEXTURE;

	public static void initialise()
	{
		PLAYER_IDLE = initBitmap(R.drawable.alienblue, 90, 125);
		PLAYER_HURT = initBitmap(R.drawable.alienblue_hurt, 90, 125);
		PLAYER_DIED = initBitmap(R.drawable.alienblue_died, 90, 125);

		CLOCK = initBitmap(R.drawable.clock, 115, 115);

		BLANK_IMAGE = initBitmap(R.drawable.blank_image, 90, 125);

		FIRE_TEXTURE = initBitmap(R.drawable.fire_particle_16, 18, 18);
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

		private static int bulletX = 100;
		private static int bulletY = 32;
	}
}
