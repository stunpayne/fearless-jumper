package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.component.Component;

import java.util.List;

/**
 * Created by anand.verma on 09/04/18 10:25 PM.
 */

public class ShapeRenderable extends Component
{
	public ShapeRenderable()
	{
		super(ShapeRenderable.class);
	}

	public List<Shape> getRenderables(){
		return null;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return null;
	}
}

