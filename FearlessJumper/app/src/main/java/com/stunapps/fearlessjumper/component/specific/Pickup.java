package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

import lombok.Getter;

/**
 * Created by sunny.s on 15/02/18.
 */

@Getter
public class Pickup extends Component
{
	private PickupType type;
	private float pickupValue;

	public enum PickupType
	{
		CLOCK, FUEL;
	}

	public Pickup(PickupType type, float pickupValue)
	{
		super(Pickup.class);
		this.type = type;
		this.pickupValue = pickupValue;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Pickup(type, pickupValue);
	}
}
