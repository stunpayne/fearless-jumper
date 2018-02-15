package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Delta;

/**
 * Created by sunny.s on 03/01/18.
 */

public class SpriteComponent extends RenderableComponent<Bitmap>
{
    private Bitmap sprite;

    public SpriteComponent()
    {
        super(RenderType.SPRITE, Delta.ZERO, 0, 0);

    }

    public SpriteComponent(Bitmap sprite, Delta delta, float width, float height)
    {
        super(RenderType.SPRITE, delta, width, height);
        this.sprite = sprite;
    }

    public void setSprite(Bitmap sprite){
        this.sprite = sprite;
    }

    public Bitmap getSprite()
    {
        return sprite;
    }

    @Override
    public Bitmap getRenderable()
    {
        return sprite;
    }

    @Override
    public SpriteComponent clone() throws CloneNotSupportedException
    {
        return new SpriteComponent(sprite, delta, width, height);
    }
}
