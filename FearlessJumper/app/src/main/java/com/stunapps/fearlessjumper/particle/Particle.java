package com.stunapps.fearlessjumper.particle;

import com.stunapps.fearlessjumper.helper.Environment.Constants;
import com.stunapps.fearlessjumper.model.Acceleration;
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
	public Acceleration acceleration;
	public float velocityAngle;
	public float speed;
	public float accelerationAngle;
	public float accelerationRate;
	public long life;
	public float lifeTimer;
	public float alpha;
	public long waitTime;

	public Particle()
	{
		this.waitTime = Long.MAX_VALUE;
		this.alpha = 1;
		this.isActive = false;
	}

	public void setVelocity(float velocityAngle, float speed)
	{
		this.velocityAngle = velocityAngle;
		this.speed = speed;
		double angleInRadians = Math.toRadians(velocityAngle);
		this.velocity = new Velocity((float) Math.cos(angleInRadians) * speed,
									 -(float) Math.sin(angleInRadians) * speed);
	}

	public void setAcceleration(float accelerationAngle, float accelerationRate)
	{
		this.accelerationAngle = accelerationAngle;
		this.accelerationRate = accelerationRate;
		double angleInRadians = Math.toRadians(accelerationAngle);
		this.acceleration = new Acceleration((float) Math.cos(angleInRadians) * accelerationRate,
											 -(float) Math.sin(angleInRadians) * accelerationRate);
	}

	public void scaleVelocity(float scaleFactor)
	{
		this.velocity.x *= scaleFactor;
		this.velocity.y *= scaleFactor;
	}

	public void setPosition(float x, float y)
	{
		this.position = new Position(x, y);
	}

	public void setLifeTimer(long life)
	{
		this.life = life;
		this.lifeTimer = life;
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

		lifeTimer -= delta / Constants.ONE_MILLION;
		if (lifeTimer > 0)
		{
			position.x += velocity.x;
			position.y += velocity.y;

			//Update velocity.
			double relativeAccAngleInRadians = Math.toRadians((velocityAngle + accelerationAngle) % 360);
			float xAccRate = (float)Math.cos(relativeAccAngleInRadians) * accelerationRate;
			float yAccRate = -(float)Math.sin(relativeAccAngleInRadians) * accelerationRate;
			velocity.x += xAccRate;
			velocity.y += yAccRate;
			velocityAngle = getAngle(velocity.x, velocity.y);



			alpha = lifeTimer / life;//calcAlpha();
			isActive = true;
			return true;
		}
		else
		{
			alpha = 0;
			lifeTimer = 0;
			isActive = false;
			return false;
		}
	}

	public static float getAngle(float x, float y)
	{
		return (float)(1.5 * Math.PI - Math.atan2(y,x)); //note the atan2 call, the order of
		// paramers is y then x
	}

	private float calcAlpha()
	{
		if (lifeTimer < 2 * life / 5)
		{
			return 2 * lifeTimer / life;
		}
		return 1;
	}
}
