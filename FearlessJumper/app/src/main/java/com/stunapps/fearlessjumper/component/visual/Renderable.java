package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 11/01/18.
 */

public class Renderable extends Component
{
	private Bitmap sprite;

	public Vector2D delta;
	public float width;
	public float height;
	public boolean isVisible;

	public Renderable(Bitmap sprite, Vector2D delta, float width, float height)
	{
		super(Renderable.class);
		this.sprite = sprite;
		this.delta = delta;
		this.width = width;
		this.height = height;
		this.isVisible = false;
	}

	public Bitmap getRenderable()
	{
		return sprite;
	}

	public Center getCenter(Position position)
	{
		return new Center(position.x + delta.getX() + width / 2,
				position.y + delta.getY() + height / 2);
	}

	public void setSprite(Bitmap sprite)
	{
		this.sprite = sprite;
	}

	public void setVisible(boolean visible)
	{
		isVisible = visible;
	}

	@Override
	public Renderable cloneComponent() throws CloneNotSupportedException
	{
		return new Renderable(sprite, delta, width, height);
	}
}
