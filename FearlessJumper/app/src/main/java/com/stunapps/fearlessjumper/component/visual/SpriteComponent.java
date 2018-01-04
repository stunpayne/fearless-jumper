package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;

/**
 * Created by sunny.s on 03/01/18.
 */

public class SpriteComponent extends Component
{
    private Bitmap sprite;

    public SpriteComponent(Bitmap sprite)
    {
        this.sprite = sprite;
        this.componentType = SpriteComponent.class;
    }

    public Bitmap getSprite()
    {
        return sprite;
    }
}
