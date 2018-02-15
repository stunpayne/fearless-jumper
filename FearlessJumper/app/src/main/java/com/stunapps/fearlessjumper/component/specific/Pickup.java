package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class Pickup extends Component
{
	private PickupType type;
	private float pickupValue;

	public enum PickupType
	{
		CLOCK, FUEL;
	}

	public Pickup(PickupType type)
	{
		super(Pickup.class);
		this.type = type;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Pickup(type);
	}
}
