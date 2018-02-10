package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.visual.AnimatorComponent;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;

import static com.stunapps.fearlessjumper.system.update.MovementUpdateSystem.MovementUpdater
        .updatePeriodicMotion;

/**
 * Created by sunny.s on 21/01/18.
 */

public class MovementUpdateSystem implements UpdateSystem
{
    private final ComponentManager componentManager;

    private static long lastProcessTime = 0;

    @Inject
    public MovementUpdateSystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        lastProcessTime = System.currentTimeMillis();

        Set<Entity> periodicEntities = componentManager.getEntities(PeriodicTranslation.class);

        for (Entity entity : periodicEntities)
        {
            PeriodicTranslation periodicTranslationComponent = entity
                    .getComponentV2(PeriodicTranslation.class);
            updatePeriodicMotion(entity, periodicTranslationComponent);
        }
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }


    public static class MovementUpdater
    {
        public static void updatePeriodicMotion(Entity entity,
                                                PeriodicTranslation periodicTranslation)
        {
            if (periodicTranslation.movesInX())
            {
                moveEntityHorizontally(entity, periodicTranslation);
            }

            if (periodicTranslation.movesInY())
            {
                moveEntityVertically(entity, periodicTranslation);
            }
        }

        private static void moveEntityHorizontally(Entity entity,
                                                   PeriodicTranslation periodicTranslation)
        {
            float deltaX = periodicTranslation.getCurrSpeedX();
            if (deltaX + entity.transform.position.x >= periodicTranslation.maxX)
            {
                deltaX = periodicTranslation.maxX - entity.transform.position.x;
                periodicTranslation.setCurrSpeedX(-1 * periodicTranslation.getCurrSpeedX());
                //TODO: need to write it in more cleaner way.
                ((AnimatorComponent)entity.getComponent(RenderableComponent.class)).triggerEvent(AnimationEvent.TURN_LEFT);
            }
            if (deltaX + entity.transform.position.x <= periodicTranslation.minX)
            {
                deltaX = periodicTranslation.minX - entity.transform.position.x;
                periodicTranslation.setCurrSpeedX(-1 * periodicTranslation.getCurrSpeedX());
                ((AnimatorComponent)entity.getComponent(RenderableComponent.class)).triggerEvent(AnimationEvent.TURN_RIGHT);
            }

            entity.transform.position.x += deltaX;
        }

        private static void moveEntityVertically(Entity entity,
                                                 PeriodicTranslation periodicTranslation)
        {
            float deltaY = periodicTranslation.getCurrSpeedY();
            if (deltaY + entity.transform.position.y >= periodicTranslation.maxY)
            {
                deltaY = periodicTranslation.maxY - entity.transform.position.y;
                periodicTranslation.setCurrSpeedY(-1 * periodicTranslation.getCurrSpeedY());
            }
            if (deltaY + entity.transform.position.y <= periodicTranslation.minY)
            {
                deltaY = periodicTranslation.minY - entity.transform.position.y;
                periodicTranslation.setCurrSpeedY(-1 * periodicTranslation.getCurrSpeedY());
            }

            entity.transform.position.y += deltaY;
        }
    }
}
