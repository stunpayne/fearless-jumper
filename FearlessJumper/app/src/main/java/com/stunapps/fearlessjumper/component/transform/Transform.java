package com.stunapps.fearlessjumper.component.transform;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Position;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by sunny.s on 03/01/18.
 */

@Getter
@Builder
@ToString
public class Transform extends Component
{
	public static final Transform ORIGIN =
			new Transform(Position.ORIGIN, Rotation.NO_ROTATION, Scale.UNIT_SCALE);

	public Position position = new Position();
	public Rotation rotation = new Rotation();
	public Scale scale = new Scale();

	public Transform(Position position)
	{
		super(Transform.class);
		this.position = position;
		this.rotation = Rotation.NO_ROTATION;
		this.scale = Scale.UNIT_SCALE;
	}

	public Transform(Position position, Rotation rotation, Scale scale)
	{
		super(Transform.class);
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public static Transform atPosition(float posX, float posY)
	{
		return new Transform(new Position(posX, posY));
	}

	public static Transform withXShift(Transform original, float deltaX)
	{
		return new Transform(new Position(original.getPosition().getX() + deltaX,
				original.getPosition().getY()));
	}

	public static Transform withYShift(Transform original, float deltaY)
	{
		return new Transform(new Position(original.getPosition().getX(),
				original.getPosition().getY() + deltaY));
	}

	public Transform translateOrigin(Transform origin)
	{
		return new Transform(position.translateOrigin(origin.getPosition()));
	}

	public void translateX(float deltaX)
	{
		position.setX(position.getX() + deltaX);
	}

	public void translateY(float deltaY)
	{
		position.setY(position.getY() + deltaY);
	}

	@Override
	public Transform clone() throws CloneNotSupportedException
	{
		return new Transform(new Position(position), new Rotation(rotation), new Scale(scale));
	}

	@ToString
	public static class Rotation
	{
		public float azimuth;

		public static Transform.Rotation NO_ROTATION = new Transform.Rotation(0);

		public Rotation()
		{
			azimuth = 0;
		}

		public Rotation(Rotation other)
		{
			this.azimuth = other.azimuth;
		}

		public Rotation(float azimuth)
		{
			this.azimuth = azimuth;
		}
	}

	@ToString
	public static class Scale
	{
		public float x;
		public float y;

		public static Scale UNIT_SCALE = new Scale(1, 1);

		public Scale()
		{
			x = y = 1;
		}

		public Scale(Scale other)
		{
			this.x = other.x;
			this.y = other.y;
		}

		public Scale(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
	}
}
