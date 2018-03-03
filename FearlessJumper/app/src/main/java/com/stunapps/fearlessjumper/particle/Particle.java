package com.stunapps.fearlessjumper.particle;

import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by anand.verma on 02/03/18.
 */

public class Particle
{
	public boolean isActive;
	public Position position;
	public Velocity velocity;
	public long totalLife;
	public float life;
	public float alpha;
	public long activationWaitTime;

	public Particle()
	{
		this.activationWaitTime = Long.MAX_VALUE;
		this.alpha = 1;
		this.isActive = false;
	}

	public void setVelocity(float angle, float speed)
	{
		double angleInRadians = Math.toRadians(angle);
		this.velocity =
				new Velocity((float) Math.cos(angleInRadians) * speed, -(float) Math.sin
						(angleInRadians) * speed);
	}

	public void setPosition(float x, float y)
	{
		this.position = new Position(x, y);
	}

	public void setLife(long life)
	{
		this.totalLife = life;
		this.life = life;
	}

	long time = 0;

	public boolean update(long delta)
	{
		long milliDelta = delta / 1000000;
		if (this.activationWaitTime > 0)
		{
			activationWaitTime -= milliDelta;
			return true;
		}

		if (life > 0)
		{
			position.x += velocity.x;
			position.y += velocity.y;
			life -= delta / 1000000;
			alpha = life / totalLife;
			return true;
		}
		else
		{
			alpha = 0;
			life = 0;
			isActive = false;
			return false;
		}
	}
}
