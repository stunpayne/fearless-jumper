package com.stunapps.fearlessjumper.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * @author sunny.s
 * @since 20/02/18
 * <p>
 * A class that provides parallax (repeating) background functionality.
 * Right now, it just provides vertical parallax.
 */

public class ParallaxBackground
{
	private final Bitmap bitmap;
	private Canvas canvas;
	private final int displayWidth;
	private final int displayHeight;

	public ParallaxBackground(Bitmap bitmap, int displayWidth, int displayHeight)
	{
		this.bitmap = bitmap;
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
		canvas = new Canvas();
	}

	/**
	 * Function that provides a list of all the bitmaps to be drawn on the screen along with
	 * their positions
	 *
	 * @return list of bitmaps to be drawn on screen
	 */
	public List<ParallaxDrawable> getDrawables(float position)
	{
		List<ParallaxDrawable> drawables = Lists.newArrayList();
		int cutPosition = (int) getSeparatingPosition(position);

		if (displayHeight - cutPosition > 0)
		{
			int startY = scaleHeightToBitmap(bitmap, cutPosition, displayHeight);
			int height = scaleHeightToBitmap(bitmap, displayHeight - cutPosition, displayHeight);
			//			Bitmap first = Bitmap.createBitmap(this.bitmap, 0, startY, bitmap.getWidth
			// (), height);
			drawables.add(new ParallaxDrawable(bitmap, new Rect(0, startY, bitmap.getWidth(),
																startY + height),
											   new Rect(0, 0, displayWidth,
														displayHeight - cutPosition)));
		}

		if (cutPosition > 0 && cutPosition < displayHeight)
		{
			int height = scaleHeightToBitmap(bitmap, cutPosition, displayHeight);
			//			Bitmap second = Bitmap.createBitmap(this.bitmap, 0, 0, bitmap.getWidth(),
			// height);
			drawables.add(new ParallaxDrawable(bitmap, new Rect(0, 0, bitmap.getWidth(), height),
											   new Rect(0, displayHeight - cutPosition,
														displayWidth, displayHeight)));
		}

		return drawables;
	}

	private float getSeparatingPosition(float position)
	{
		return position > 0 ? position % displayHeight : displayHeight + (position %
				displayHeight);
	}

	private int scaleHeightToBitmap(Bitmap bitmap, int height, int max)
	{
		return (int) (((float) height / (float) max) * bitmap.getHeight());
	}

	@ToString
	@Getter
	public class ParallaxDrawable
	{
		private Bitmap bitmap;
		private Rect cropRect;
		private Rect renderRect;

		public ParallaxDrawable(Bitmap bitmap, Rect cropRect, Rect renderRect)
		{
			this.bitmap = bitmap;
			this.cropRect = cropRect;
			this.renderRect = renderRect;
		}
	}
}
