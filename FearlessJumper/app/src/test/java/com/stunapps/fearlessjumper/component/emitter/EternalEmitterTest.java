package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * Created by sunny.s on 16/03/18.
 */

public class EternalEmitterTest
{
	private static ComponentManager componentManager;
	private static EntityManager entityManager;

	@BeforeClass
	public static void setUp()
	{
		componentManager = new GameComponentManager();
		entityManager = new EntityManager(componentManager);
	}

	@Test
	public void testEmissionCount()
	{
		EternalEmitter eternalEmitter = new EternalEmitter(
				EmitterConfig.builder().maxParticles(100).particleLife(1000).emissionRate(10)
						.maxSpeed(6f).build());
		eternalEmitter.setEntity(
				new Entity(componentManager, entityManager, Transform.ORIGIN, 1241412124));
		eternalEmitter.init();
		for (int i = 0; i < 120; i++)
		{
			eternalEmitter.update(17000000);
			System.out.println("Frame: " + (i + 1) + " Num of particles: " +
					eternalEmitter.getLiveParticles());
		}
	}

	@Test
	public void testAngleFormula()
	{
		Random random = new Random();
		double[] angles = new double[]{180, 90, 0, -90, -180};
		for (double angle : angles)
		{
			double angleInRadians = Math.toRadians(angle);
			double x = Math.cos(angleInRadians);
			double y = -Math.cos(angleInRadians);
			System.out.println(
					"Angle: " + angle + " radians: " + angleInRadians + " x: " + x + " y:" + y);
		}

		for (int i = 0; i < 50; i++)
		{
			float rand = random.nextFloat();
			float multiplier = 2 * (0.5f - rand);
			float angle = 180 * multiplier;
			System.out.println("Rand: " + rand + " multiplier: " + multiplier + "Angle: " + angle);
			Assert.assertTrue("Angle generated is out of range", (-180f < angle) && (angle <
					180f));
		}
	}
}
