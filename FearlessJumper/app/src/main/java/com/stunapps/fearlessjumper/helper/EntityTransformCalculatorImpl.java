package com.stunapps.fearlessjumper.helper;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.prefab.Prefab;

/**
 * Created by sunny.s on 13/01/18.
 */

public class EntityTransformCalculatorImpl implements EntityTransformCalculator
{
    @Override
    public int getWidth(Entity entity)
    {
        return 0;
    }

    @Override
    public int getHeight(Entity entity)
    {
        return 0;
    }

    @Override
    public int getWidth(Prefab prefab)
    {
        Component component = prefab.getComponent(RenderableComponent.class);
        if (component != null)
        {
            RenderableComponent renderable = (RenderableComponent) component;
            if (renderable.renderType == RenderableComponent.RenderType.SPRITE)
            {
                return ((Bitmap) renderable.getRenderable()).getWidth();
            }
        }
        return 0;
    }

    @Override
    public int getHeight(Prefab prefab)
    {
        return 0;
    }
}
