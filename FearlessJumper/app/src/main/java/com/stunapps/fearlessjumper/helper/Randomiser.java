package com.stunapps.fearlessjumper.helper;

import java.util.Random;

/**
 * @author: sunny.s
 * @since 26/04/18.
 */

public class Randomiser
{
	private static final Random random = new Random();

	/**
	 * Method to generate a pseudo-random float between -value and value
	 *
	 * @return A pseudo-random float between -value and value
	 */
	public static float twoWayRandom(float value)
	{
		return value * twoWayRandom();
	}

	/**
	 * Method to generate a pseudo-random float between -1.0 and 1.0
	 *
	 * @return A pseudo-random float between -1.0 and 1.0
	 */
	public static float twoWayRandom()
	{
		return 2 * (0.5f - random.nextFloat());
	}

	/**
	 * random.nextFloat() returns a float between 0 and 1.
	 * Multiplying that by the bound param yields a float between 0 and the bound
	 *
	 * @param bound the max random number to be generated
	 * @return a random number between 0 and the bound
	 */
	public static float nextFloat(float bound)
	{
		return bound * random.nextFloat();
	}

	/**
	 * Returns a random float between min and max
	 *
	 * @param min the min random number to be generated
	 * @param max the max random number to be generated
	 * @return a random number between min and max
	 */
	public static float nextFloat(float min, float max)
	{
		if (max < min)
		{
			throw new IllegalArgumentException();
		}

		return min + (max - min) * random.nextFloat();
	}
}
