package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 11/01/18.
 */

public abstract class RenderableComponent<RenderableType> extends Component
{
    public RenderType renderType;

    public RenderableComponent(RenderType renderType)
    {
        this.renderType = renderType;
        this.componentType = RenderableComponent.class;
    }

    public enum RenderType
    {
        ANIMATOR,
        SPRITE
    }

    public abstract RenderableType getRenderable();
}
