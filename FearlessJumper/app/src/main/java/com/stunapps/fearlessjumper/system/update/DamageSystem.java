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
        Health health1 = (Health) entity1.getComponent(Health.class);
        DamageComponent damageComponent1 = (DamageComponent) entity1.getComponent(DamageComponent.class);

        Health health2 = (Health) entity2.getComponent(Health.class);
        DamageComponent damageComponent2 = (DamageComponent) entity2.getComponent(DamageComponent.class);

        if (health1 != null && damageComponent2 != null)
        {
            AnimatorComponent animatorComponent = ((AnimatorComponent) entity1.getComponent(RenderableComponent.class));
            if (health1.damageHealth(damageComponent2.damage()))
            {
                //Log.d(TAG, "applyCollision: damage between entity1 and entity2, entity's new health = " + health1.getHealth());
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
                //Log.d(TAG, "applyCollision: damage between entity2 and entity1, entity's new health = " + health2.getHealth());
                animatorComponent.triggerEvent(AnimationEvent.TERMINATE);
            } else
            {
                animatorComponent.triggerEvent(AnimationEvent.HURT);
            }
        }
    }
}
