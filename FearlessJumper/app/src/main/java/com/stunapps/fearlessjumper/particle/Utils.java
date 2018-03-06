package com.stunapps.fearlessjumper.particle;

/**
 * Created by anand.verma on 06/03/18.
 */

public class Utils
{
	public static float getAngle(float x, float y)
	{
		return (float) Math.toDegrees(Math.atan2(-y, x));
	}
}
