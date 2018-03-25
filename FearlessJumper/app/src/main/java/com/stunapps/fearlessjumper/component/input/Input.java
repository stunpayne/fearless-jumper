package com.stunapps.fearlessjumper.component.input;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 12/01/18.
 */

public class Input extends Component
{
	private boolean respondsToTouch;
	private boolean respondsToSensor;

	public Input(boolean respondsToTouch, boolean respondsToSensor)
	{
		super(Input.class);
		this.respondsToTouch = respondsToTouch;
		this.respondsToSensor = respondsToSensor;
	}

	public boolean respondsToTouch()
	{
		return respondsToTouch;
	}

	public boolean respondsToSensor()
	{
		return respondsToSensor;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Input(respondsToTouch, respondsToSensor);
	}
}
