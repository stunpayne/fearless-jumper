package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.transform.Position;

/**
 * Created by sunny.s on 11/01/18.
 */

public abstract class RenderableComponent<RenderableType> extends Component
{
    public RenderType renderType;
    public Delta delta;
    public float width;
    public float height;

    public RenderableComponent(RenderType renderType, Delta delta, float width, float height)
    {
        super(RenderableComponent.class);
        this.renderType = renderType;
        this.delta = delta;
        this.width = width;
        this.height = height;
    }

    public enum RenderType
    {
        ANIMATOR,
        SPRITE
    }

    public abstract RenderableType getRenderable();

    public Center getCenter(Position position)
    {
        return new Center(position.x + delta.x + width / 2, position.y + delta.y + height / 2);
    }
}
