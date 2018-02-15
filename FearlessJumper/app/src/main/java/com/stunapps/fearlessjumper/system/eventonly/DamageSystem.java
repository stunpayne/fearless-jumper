package com.stunapps.fearlessjumper.system.eventonly;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.System;
import com.stunapps.fearlessjumper.system.listener.CollisionListener;
import com.stunapps.fearlessjumper.system.model.CollisionResponse;
import com.stunapps.fearlessjumper.system.update.CollisionSystem;

/**
 * Created by anand.verma on 03/02/18.
 */

public class DamageSystem implements System, CollisionListener
{
    @Inject
    public DamageSystem(CollisionSystem collisionSystem)
    {
        collisionSystem.registerObserver(this);
    }

    @Override
    public void onCollision(Entity entity1, Entity entity2, CollisionResponse collisionResponse, long deltaTime)
    {
        Health health1 = entity1.getComponent(Health.class);
        DamageComponent damageComponent1 = entity1.getComponent(DamageComponent.class);

        Health health2 = entity2.getComponent(Health.class);
        DamageComponent damageComponent2 = entity2.getComponent(DamageComponent.class);

        if (health1 != null && damageComponent2 != null)
        {
            Animator animator = ((Animator) entity1.getComponent(RenderableComponent.class));
            health1.takeDamage(damageComponent2.damage());
            if (health1.isOver())
            {
                animator.triggerEvent(AnimationEvent.TERMINATE);
            } else
            {
                animator.triggerEvent(AnimationEvent.HURT);
            }
        }

        if (health2 != null && damageComponent1 != null)
        {
            Animator animator = ((Animator) entity2.getComponent(RenderableComponent.class));
            health2.takeDamage(damageComponent1.damage());
            if (health2.isOver())
            {
                animator.triggerEvent(AnimationEvent.TERMINATE);
            } else
            {
                animator.triggerEvent(AnimationEvent.HURT);
            }
        }
    }
}
