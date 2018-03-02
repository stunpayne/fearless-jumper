package com.stunapps.fearlessjumper.helper;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 26/01/18.
 */

@Singleton
public class CanvasUtils
{
    public static Position getDistanceBetween(Entity entity1, Entity entity2)
    {
        return entity1.transform.position.distanceFrom(entity2.transform.position);
    }
}
