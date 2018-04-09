package com.stunapps.fearlessjumper;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

import com.stunapps.fearlessjumper.game.Environment.Device;

import org.junit.Test;
import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * Created by sunny.s on 20/02/18.
 */

public class RandomTest
{
	private static final int MAX_RECTS = 1;

	@Test
	public void testMod()
	{
		int height = 1000;
		int pos = -100;

		int separatingPosition = getSeparatingPosition(pos, height);
		System.out.println("At position: " + pos);
		System.out.println("Remainder with height: " + pos % height);
		System.out.println("First part from " + separatingPosition + " to " + height);
		System.out.println("Second part from 0 to " + separatingPosition);

		pos = -1000;
		separatingPosition = getSeparatingPosition(pos, height);
		System.out.println("\n\nAt position: " + pos);
		System.out.println("Remainder with height: " + pos % height);
		System.out.println("First part from " + separatingPosition + " to " + height);
		System.out.println("Second part from 0 to " + separatingPosition);
	}

	private int getSeparatingPosition(int pos, int height)
	{
		return pos > 0 ? pos % height : pos % height + height;
	}

	@Test
	public void testBitmapScaling()
	{
		int actual = 40, height = 50, max = 100;

		System.out.println("Int height: " + height);
		System.out.println("Float height: " + (float) height);
		System.out.println("Scaled height: " + scaleHeightToBitmap(actual, height, max));
	}

	@Test
	public void testColorStep()
	{
		AngledRect angledRect = new AngledRect();
		System.out.println("testColorStep: " + angledRect.color);
		System.out.println("testColorStep: " + angledRect.updateColor());
		System.out.println("testColorStep: " + angledRect.updateColor());
	}

	private int scaleHeightToBitmap(int actual, int height, int max)
	{
		return (int) (((float) height / (float) max) * actual);
	}

	private static class AngledRect
	{
		public static int MAX_VARIATION = 200;

		private static Paint paint = null;

		public Rect rect;
		public int angle;
		public int color;
		public int colorStep;

		public static List<AngledRect> init()
		{
			final Random random = new Random();
			List<AngledRect> angledRectList = Lists.newArrayList();
			int colorDiff = Color.RED - Color.BLUE;

			for (int i = 0; i < MAX_RECTS; i++)
			{
				int width = random.nextInt(MAX_VARIATION);
				int height = random.nextInt(MAX_VARIATION);
				int left = random.nextInt(Device.SCREEN_WIDTH);
				int top = random.nextInt(Device.SCREEN_HEIGHT);


				AngledRect angledRect = new AngledRect();
				angledRect.angle = random.nextInt(360);
				angledRect.rect = new Rect(left, top, left + width, top + height);
				angledRect.colorStep = random.nextInt(colorDiff);
				angledRect.color = Color.BLUE;

				angledRectList.add(angledRect);
			}

			return angledRectList;
		}

		public static Paint paint()
		{
			if (paint == null)
			{
				paint = new Paint();
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.WHITE);
			}
			return paint;
		}

		public int updateColor()
		{
			color += colorStep;
			color %= Color.RED;
			return color;
		}
	}
}
