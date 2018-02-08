package com.stunapps.fearlessjumper.system.model;

/**
 * Created by anand.verma on 02/02/18.
 */

public class CollisionResponse
{
    public CollisionFace collisionFace;

    public CollisionResponse(CollisionFace collisionFace)
    {
        this.collisionFace = collisionFace;
    }

    /**
     * HORIZONTAL - first entity's right collides with second entity's left.
     * HORIZONTAL_REVERSE - first entity's left collides with second entity's right.
     * VERTICAL - first entity's bottom collides with second entity's top.
     * VERTICAL_REVERSE - first entity's top collides with second entity's bottom.
     */
    public static enum CollisionFace {
        HORIZONTAL,
        HORIZONTAL_REVERSE,
        VERTICAL,
        VERTICAL_REVERSE
    }
}
