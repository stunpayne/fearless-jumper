package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent.Velocity;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.Set;

/**
 * Created by sunny.s on 14/01/18.
 */

public class TransformUpdateSystem implements UpdateSystem
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private static long lastProcessTime = System.nanoTime();

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
        lastProcessTime = System.currentTimeMillis();
        Set<Entity> movables = componentManager.getEntities(PlayerComponent.class);

        for (Entity movable : movables)
        {
            PhysicsComponent physicsComponent = (PhysicsComponent) movable.getComponent(
                    PhysicsComponent.class);
            RenderableComponent renderable = (RenderableComponent) movable.getComponent(
                    RenderableComponent.class);
            if (movable.hasComponent(PlayerComponent.class))
                movePlayerTransform(movable.transform, physicsComponent.velocity, renderable);
            else
                moveTransform(movable.transform, physicsComponent.velocity);
        }
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }

    private void movePlayerTransform(Transform transform, Velocity velocity,
                                     RenderableComponent renderable)
    {
        transform.position.x += velocity.x;
        transform.position.y += velocity.y;

        transform.position.x = Math.min(transform.position.x,
                Constants.SCREEN_WIDTH - renderable.width);
        transform.position.x = Math.max(transform.position.x, 0);
//        transform.position.y = Math.min(transform.position.y,
//                Constants.SCREEN_HEIGHT - renderable.height);
//        transform.position.y = Math.max(transform.position.x, 0);
    }

    private void moveTransform(Transform transform, Velocity velocity)
    {
        transform.position.x += velocity.x;
        transform.position.y += velocity.y;
    }
}
