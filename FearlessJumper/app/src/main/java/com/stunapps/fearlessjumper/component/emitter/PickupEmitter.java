package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.model.Particle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anand.verma on 02/03/18.
 */

public class PickupEmitter extends BaseEmitter
{
	List<Particle> particles = new LinkedList<>();

	public PickupEmitter()
	{
		super(PickupEmitter.class, 2000, 100);
	}

	@Override
	public void init()
	{
		int particleCount = 10;

		particles = new LinkedList<>();

		float angle = 0;
		for (int i = 0; i < particleCount; i++)
		{
			Particle particle = new Particle();
			particle.setPosition(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2);
			particle.setLife(5000l);
			particle.setVelocity(angle, 1.0f);
			angle += 30;
			angle += angle % 360;
			particles.add(particle);
		}
	}

	@Override
	public void update(long delta)
	{
		for (Particle particle : particles)
		{
			//particle.position.x += particle.velocity.x * delta;
			//particle.position.y += particle.velocity.y * delta;
		}
	}

	public void drawParticles(Canvas canvas)
	{
		/*
		for (Particle particle : particles)
		{
			canvas.drawPoint(particle.position.x, particle.position.y, new Paint(Color.RED));
		} */
	}
}
