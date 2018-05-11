package com.stunapps.fearlessjumper;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anand.verma on 10/05/18.
 */

public class CircularMotionTest
{
	@Test
	public void testCircularMotion()
	{
		float omega = 3.0f; // theta/time
		float radius = 5.0f;
		float radialAcceleration = omega * omega * radius;

		int frames = 360;

		float XVelocity = 0.0f;
		float YVelocity = 5.0f;

		float theta = 0.0f;

		List<Float> xVelocityList = new LinkedList<>();
		List<Float> yVelocityList = new LinkedList<>();

		for (int i = 0; i < frames; i++)
		{
			xVelocityList.add(XVelocity);
			yVelocityList.add(YVelocity);
			YVelocity = YVelocity + radialAcceleration * (float) Math.sin(Math.toRadians(theta));
			XVelocity = XVelocity + radialAcceleration * (float) Math.cos(Math.toRadians(theta));
			theta += omega;
			if (i >= 120)
			{
				System.out.println("x compare = " + (Math.round(xVelocityList.get(i)) ==
						Math.round(xVelocityList.get(i - 120))));
				System.out.println("y compare = " + (Math.round(yVelocityList.get(i)) ==
						Math.round(yVelocityList.get(i - 120))));
			}
		}

		System.out.println("XVelocity = " + Math.round(XVelocity));
		System.out.println("YVelocity = " + Math.round(YVelocity));
	}
}
