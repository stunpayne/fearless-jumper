package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.visual.AnimatorComponent;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.System;
import com.stunapps.fearlessjumper.system.listener.CollisionListener;
import com.stunapps.fearlessjumper.system.model.CollisionResponse;

/**
 * Created by anand.verma on 03/02/18.
 */

public class DamageSystem implements System, CollisionListener
{
    @Override
    public void applyCollision(Entity entity1, Entity entity2, CollisionResponse collisionResponse, long deltaTime)
    {
        Health health1 = entity1.getComponentV2(Health.class);
        DamageComponent damageComponent1 = entity1.getComponentV2(DamageComponent.class);

        Health health2 = entity2.getComponentV2(Health.class);
        DamageComponent damageComponent2 = entity2.getComponentV2(DamageComponent.class);

        if (health1 != null && damageComponent2 != null)
        {
            AnimatorComponent animatorComponent = ((AnimatorComponent) entity1.getComponent(RenderableComponent.class));
            if (health1.damageHealth(damageComponent2.damage()))
            {
                animatorComponent.triggerEvent(AnimationEvent.TERMINATE);
            } else
            {
                animatorComponent.triggerEvent(AnimationEvent.HURT);
            }
        }

        if (health2 != null && damageComponent1 != null)
        {
            AnimatorComponent animatorComponent = ((AnimatorComponent) entity2.getComponent(RenderableComponent.class));
            if (health2.damageHealth(damageComponent1.damage()))
            {
                animatorComponent.triggerEvent(AnimationEvent.TERMINATE);
            } else
            {
                animatorComponent.triggerEvent(AnimationEvent.HURT);
            }
        }
    }
}
