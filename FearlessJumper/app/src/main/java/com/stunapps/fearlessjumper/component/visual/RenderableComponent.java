package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;

/**
 * Created by sunny.s on 11/01/18.
 */

public abstract class RenderableComponent<RenderableType> extends Component
{
    public RenderType renderType;
    public Delta delta;

    public RenderableComponent(RenderType renderType, Delta delta)
    {
        super(RenderableComponent.class);
        this.renderType = renderType;
        this.delta = delta;
    }

    public enum RenderType
    {
        ANIMATOR,
        SPRITE
    }

    public abstract RenderableType getRenderable();
}
