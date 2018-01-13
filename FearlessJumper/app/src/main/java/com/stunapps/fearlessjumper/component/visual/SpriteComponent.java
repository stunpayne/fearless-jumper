package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

/**
 * Created by sunny.s on 03/01/18.
 */

public class SpriteComponent extends RenderableComponent<Bitmap>
{
    private Bitmap sprite;

    public SpriteComponent()
    {
        super(RenderType.SPRITE);
    }

    public SpriteComponent(Bitmap sprite)
    {
        super(RenderType.SPRITE);
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
