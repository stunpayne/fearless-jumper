package com.stunapps.fearlessjumper.particle;

import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Velocity;

import lombok.EqualsAndHashCode;

/**
 * Created by anand.verma on 02/03/18.
 */

@EqualsAndHashCode(of = "id")
public class Particle
{
	private static final String TAG = Particle.class.getSimpleName();

	public long id;
	public boolean isActive;

	public Position position;
	public Velocity velocity;
	public VelocityScaler velocityScaler;
	public float accelerationAngle;
	public float accelerationRate;
	public long life;
	public float lifeTimer;
	public long waitTime;
	public float alpha;
	public AlphaCalculator alphaCalculator;

	public Particle(long id)
	{
		this.id = id;
		reset();
	}

	public void setPosition(Position other)
	{
		this.position.x = other.x;
		this.position.y = other.y;
	}

	public void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}

	public void setVelocity(float velocityAngle, float speed)
	{
		double angleInRadians = Math.toRadians(velocityAngle);
		this.velocity.x  = (float) Math.cos(angleInRadians) * speed;
		this.velocity.y  = -(float) Math.sin(angleInRadians) * speed;
	}

	public void setVelocityScaler(VelocityScaler velocityScaler)
	{
		this.velocityScaler = velocityScaler;
	}

	public void setAcceleration(float accelerationAngle, float accelerationRate)
	{
		this.accelerationAngle = accelerationAngle;
		this.accelerationRate = accelerationRate;
	}

	public void setLife(long life)
	{
		this.life = life;
		this.lifeTimer = life;
	}

	public void setAlpha(AlphaCalculator alphaCalculatorFunction)
	{
		this.alphaCalculator = alphaCalculatorFunction;
	}

	/**
	 * Function to update particle
	 *
	 * @param nanoDelta in nano seconds
	 * @return true if particle is alive after update, false if not
	 */
	public boolean update(long nanoDelta)
	{
		long milliDelta = nanoDelta / Time.ONE_MILLION;

		if (this.waitTime > 0)
		{
			waitTime -= milliDelta;
			return true;
		}

		lifeTimer -= milliDelta;

		if (lifeTimer > 0)
		{
			//Update position
			position.x += velocity.x;
			position.y += velocity.y;

			//Update velocity.
			float velocityAngle = Utils.getAngleInDegree(velocity.x, velocity.y);
			double relativeAccelerationAngle = Math.toRadians((velocityAngle + accelerationAngle) % 360);
			float xAccelerationRate = (float)Math.cos(relativeAccelerationAngle) * accelerationRate;
			float yAccelerationRate = -(float)Math.sin(relativeAccelerationAngle) * accelerationRate;

			velocity.x += xAccelerationRate;
			velocity.y += yAccelerationRate;

			//Scale velocity
			if (null != velocityScaler)
			{
				velocity = velocityScaler.scale(velocity, life, lifeTimer);
			}

			//Update alpha
			if (null != alphaCalculator)
			{
				alpha = alphaCalculator.calculate(life, lifeTimer);
			}

			isActive = true;
			return true;
		}
		else
		{
			reset();
			return false;
		}
	}

	public void reset()
	{
		this.isActive = false;
		position = new Position(0,0);
		velocity = new Velocity(0,0);
		this.velocityScaler = null;
		this.accelerationAngle = 0;
		this.accelerationRate = 0;
		this.life = 0;
		this.lifeTimer = 0;
		this.waitTime = 0;
		this.alpha = 1;
		this.alphaCalculator = null;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Particle particle = (Particle) o;

		return id == particle.id;
	}

	@Override
	public int hashCode()
	{
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString()
	{
		return "Particle{" + "id=" + id + ", isActive=" + isActive + ", position=" + position +
				", velocity=" + velocity + ", life=" + life + '}';
	}
}
