package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.model.Particle;
import com.stunapps.fearlessjumper.model.Velocity;

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
		int particleCount = 500;

		particles = new LinkedList<>();

		float angle = 0;
		for (int i = 0; i < particleCount; i++)
		{
			Particle particle = new Particle();
			particle.setPosition(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2);
			particle.setLife(5000l);
			particle.setVelocity(angle, 1f);
			angle += 10;
			angle = angle % 360;
			particles.add(particle);
		}
	}

	@Override
	public void update(long delta)
	{
		for (Particle particle : particles)
		{
			particle.update(delta);
		}
	}

	public void drawParticles(Canvas canvas)
	{
		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);

		for (Particle particle : particles)
		{
			fuelTextPaint.setAlpha((int)(255*particle.alpha));
			//canvas.drawText("working", particle.position.x, particle.position.y, fuelTextPaint);
			//canvas.drawPoint(particle.position.x, particle.position.y, fuelTextPaint);
			canvas.drawCircle(particle.position.x, particle.position.y, 5, fuelTextPaint);
		}
	}
}
