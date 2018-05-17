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

		int frames = 361;

		float XVelocity = 0.0f;
		float YVelocity = 5.0f;

		float x = 0.0f;
		float y = 0.0f;

		float theta = 0.0f;

		List<Float> xList = new LinkedList<>();
		List<Float> yList = new LinkedList<>();

		for (int i = 0; i < frames; i++)
		{
			System.out.println("i = " + i + " x = " + x + "  theta = " + theta + " xVel = " +
									   XVelocity);
//			System.out.println("i = " + i + " y = " + y + " yVel = " + YVelocity);

			xList.add(x);
			yList.add(y);
			YVelocity = radialAcceleration * (float) Math.sin(Math.toRadians(theta));
			XVelocity = radialAcceleration * (float) Math.cos(Math.toRadians(theta));
			theta += omega;
			if (i >= 120)
			{

				/*System.out.println("x compare = " + (Math.round(xVelocityList.get(i)) ==
						Math.round(xVelocityList.get(i - 120))));
				System.out.println("y compare = " + (Math.round(yVelocityList.get(i)) ==
						Math.round(yVelocityList.get(i - 120))));*/

			}

			x += XVelocity;
			y += YVelocity;
		}

		System.out.println("XVelocity = " + Math.round(XVelocity));
		System.out.println("YVelocity = " + Math.round(YVelocity));
	}
}
