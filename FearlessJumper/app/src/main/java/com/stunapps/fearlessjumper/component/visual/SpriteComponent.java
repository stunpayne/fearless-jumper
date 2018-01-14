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
        super(RenderType.SPRITE, new Delta(0,0));
    }

    public SpriteComponent(Bitmap sprite, Delta delta)
    {
        super(RenderType.SPRITE, delta);
        this.sprite = sprite;
    }

    public void setSprite(Bitmap bitmap){
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
}
