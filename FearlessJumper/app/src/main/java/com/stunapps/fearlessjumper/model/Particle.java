package com.stunapps.fearlessjumper.model;

import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by anand.verma on 02/03/18.
 */

public class Particle
{
	public Position position;
	public Velocity velocity;
	public long totalLife;
	public float life;
	public float alpha;

	public Particle()
	{
		this.alpha = 1;
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

	public void update(long delta)
	{
		if (life > 0)
		{
			position.x += velocity.x;
			position.y += velocity.y;

		}

		alpha = life / totalLife;
		life -= delta/1000000;
	}
}
