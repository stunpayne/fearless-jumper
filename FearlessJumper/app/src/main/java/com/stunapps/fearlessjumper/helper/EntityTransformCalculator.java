package com.stunapps.fearlessjumper.helper;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.prefab.Prefab;

/**
 * Created by sunny.s on 13/01/18.
 */

public interface EntityTransformCalculator
{
    int getWidth(Entity entity);

    int getHeight(Entity entity);

    int getWidth(Prefab prefab);

    int getHeight(Prefab prefab);
}
