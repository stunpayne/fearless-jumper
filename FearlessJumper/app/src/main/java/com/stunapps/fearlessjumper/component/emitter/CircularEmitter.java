package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;

/**
 * Created by anand.verma on 02/03/18.
 */

public class CircularEmitter extends BaseEmitter
{
	public CircularEmitter()
	{
		super(CircularEmitter.class, 10, 2000l, 1000l);
	}

	@Override
	public void init()
	{
		super.init();
	}

	@Override
	protected void setupParticleCluster(List<Particle> particles)
	{
		int size = particles.size();
		float angle = 0;
		float angleDelta = 360.0f / size;
		for (Particle particle : particles)
		{
			particle.setPosition(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2);
			particle.setVelocity(angle, 1f);
			angle += angleDelta;
			angle = angle % 360;
		}
	}

	@Override
	public void update(long delta)
	{
		super.update(delta);
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
			fuelTextPaint.setAlpha((int) (255 * particle.alpha));
			if (particle.isActive)
			{
				canvas.drawCircle(particle.position.x, particle.position.y, 5, fuelTextPaint);
				//canvas.drawPoint(particle.position.x, particle.position.y, fuelTextPaint);
			}
		}
	}
}
