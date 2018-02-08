package com.stunapps.fearlessjumper.system.listener;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse;

/**
 * Created by anand.verma on 02/02/18.
 */

public interface CollisionListener
{
    public void applyCollision(Entity entity1, Entity entity2, CollisionResponse collisionResponse, long deltaTime);
}
