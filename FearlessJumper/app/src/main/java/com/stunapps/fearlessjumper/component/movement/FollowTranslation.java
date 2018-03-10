package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 11/03/18.
 */

public class FollowTranslation extends Component
{
	public Class<? extends Component> followee;

	public FollowTranslation(Class<? extends Component> followee)
	{
		super(FollowTranslation.class);
		this.followee = followee;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return this;
	}
}
