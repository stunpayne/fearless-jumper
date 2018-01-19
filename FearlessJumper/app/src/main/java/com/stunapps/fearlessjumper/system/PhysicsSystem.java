package com.stunapps.fearlessjumper.system;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;

import static com.stunapps.fearlessjumper.helper.Constants.ONE_SECOND_NANOS;
import static com.stunapps.fearlessjumper.helper.Constants.scale;

/**
 * Created by sunny.s on 19/01/18.
 */

public class PhysicsSystem implements System
{
    private final ComponentManager componentManager;

    private static final float GRAVITY = -9.8f;

    @Inject
    public PhysicsSystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        Set<Entity> physicalEntities = componentManager.getEntities(PhysicsComponent.class);

        for (Entity entity : physicalEntities)
        {
            applyGravity(entity, deltaTime);
            Log.d("PHYSICS", "Moved entity to " + entity.transform);
        }
    }

    private void applyGravity(Entity entity, long deltaTime)
    {
        PhysicsComponent physicsComponent = (PhysicsComponent) entity.getComponent(
                PhysicsComponent.class);
        physicsComponent.velocity.y -= (GRAVITY * scale() * deltaTime / ONE_SECOND_NANOS);
    }
}
