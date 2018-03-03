package com.stunapps.fearlessjumper.particle;

import com.stunapps.fearlessjumper.helper.Environment.Constants;
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
	public long waitTime;

	public Particle()
	{
		this.waitTime = Long.MAX_VALUE;
		this.alpha = 1;
		this.isActive = false;
	}

	public void setVelocity(float angle, float speed)
	{
		double angleInRadians = Math.toRadians(angle);
		this.velocity = new Velocity((float) Math.cos(angleInRadians) * speed,
									 -(float) Math.sin(angleInRadians) * speed);
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

	/**
	 * Function to update particle
	 *
	 * @param delta in nano seconds
	 * @return true if particle is alive after update, false if not
	 */
	public boolean update(long delta)
	{
		long milliDelta = delta / Constants.ONE_MILLION;
		if (this.waitTime > 0)
		{
			waitTime -= milliDelta;
			return true;
		}

		life -= delta / Constants.ONE_MILLION;
		if (life > 0)
		{
			position.x += velocity.x;
			position.y += velocity.y;
			alpha = life / totalLife;
			isActive = true;
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
