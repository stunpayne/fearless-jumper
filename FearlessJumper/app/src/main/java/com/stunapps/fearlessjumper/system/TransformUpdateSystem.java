package com.stunapps.fearlessjumper.system;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.Set;

/**
 * Created by sunny.s on 14/01/18.
 */

public class TransformUpdateSystem implements System
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;

    private final int deltaY = 1;

    @Inject
    public TransformUpdateSystem(EntityManager entityManager, ComponentManager componentManager)
    {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        Set<Entity> movables = componentManager.getEntities(PlayerComponent.class);

        for (Entity movable: movables)
        {
            PhysicsComponent physicsComponent = (PhysicsComponent) movable.getComponent(
                    PhysicsComponent.class);
            movable.transform.move(physicsComponent.velocity);
        }
    }
}
