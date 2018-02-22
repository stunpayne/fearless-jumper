package com.stunapps.fearlessjumper.core;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;

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
	/**
	 * Should be provided scaled to the display
	 */
	private Bitmap bitmap;
	private final int displayWidth;
	private final int displayHeight;

	private List<ParallaxDrawableArea> drawables = Lists.newArrayList();

	public ParallaxBackground(Bitmap bitmap, int displayWidth, int displayHeight)
	{
		this.bitmap = bitmap;
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}

	/**
	 * Function that provides a list of all the areas on the screen where the bitmap is to be
	 * drawn based on the current camera position
	 *
	 * @return list areas on screen where the bitmap is to be drawn
	 */
	public List<ParallaxDrawableArea> getDrawables(float position)
	{
		//	Clear the current values of the drawables list
		drawables.clear();

		//	Since the background is repeating, two instances of it will be drawn at any given
		// instance. This is the position where the two will meet.
		int cutPosition = (int) getCutPositionFromCameraPosition(position);

		drawables.add(new ParallaxDrawableArea(
				new Rect(0, -cutPosition, displayWidth, -cutPosition + displayHeight)));
		drawables.add(new ParallaxDrawableArea(
				new Rect(0, -cutPosition + displayHeight, displayWidth,
						-cutPosition + 2 * displayHeight)));

		return drawables;
	}

	private float getCutPositionFromCameraPosition(float cameraPosition)
	{
		return cameraPosition >= 0 ?
				cameraPosition % displayHeight : displayHeight + (cameraPosition % displayHeight);
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	@ToString
	@Getter
	public class ParallaxDrawableArea
	{
		private Rect renderRect;
		private float ratio;

		public ParallaxDrawableArea(Rect renderRect)
		{
			this(renderRect, 1f);
		}

		public ParallaxDrawableArea(@NonNull Rect renderRect, float ratio)
		{
			this.ratio = ratio;
			this.renderRect =
					new Rect((int) (renderRect.left * ratio), (int) (renderRect.top * ratio),
							(int) (renderRect.right * ratio), (int) (renderRect.bottom * ratio));
		}
	}
}
