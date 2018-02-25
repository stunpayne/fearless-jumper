package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.transform.Position;

/**
 * Created by sunny.s on 11/01/18.
 */

public class Renderable extends Component
{
    private Bitmap sprite;

    public Delta delta;
    public float width;
    public float height;

    public Renderable(Bitmap sprite, Delta delta, float width, float height)
    {
        super(Renderable.class);
        this.sprite = sprite;
        this.delta = delta;
        this.width = width;
        this.height = height;
    }

    public Bitmap getRenderable()
    {
        return sprite;
    }

    public Center getCenter(Position position)
    {
        return new Center(position.x + delta.x + width / 2, position.y + delta.y + height / 2);
    }

    public void setSprite(Bitmap sprite)
    {
        this.sprite = sprite;
    }

    @Override
    public Renderable clone() throws CloneNotSupportedException
    {
        return new Renderable(sprite, delta, width, height);
    }
}