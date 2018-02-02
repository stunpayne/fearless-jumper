package com.stunapps.fearlessjumper.system.listener;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.CollisionEvent;

/**
 * Created by anand.verma on 02/02/18.
 */

public interface CollisionListener
{
    public void applyCollision(Entity entity, CollisionEvent event);
}
