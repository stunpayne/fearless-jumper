package com.stunapps.fearlessjumper;

import org.junit.Test;

/**
 * Created by sunny.s on 20/02/18.
 */

public class RandomTest
{
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

	private int scaleHeightToBitmap(int actual, int height, int max)
	{
		return (int) (((float) height / (float) max) * actual);
	}
}
