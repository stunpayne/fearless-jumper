package com.stunapps.fearlessjumper.helper;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.prefab.Prefab;

/**
 * Created by sunny.s on 13/01/18.
 */

public interface EntityTransformCalculator
{
    float getWidth(Entity entity);

    float getHeight(Entity entity);

    float getWidth(Prefab prefab);

    float getHeight(Prefab prefab);
}
