package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
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
            PeriodicTranslation periodicTranslationComponent = (PeriodicTranslation) entity
                    .getComponent(PeriodicTranslation.class);
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
            }
            if (deltaX + entity.transform.position.x <= periodicTranslation.minX)
            {
                deltaX = periodicTranslation.minX - entity.transform.position.x;
                periodicTranslation.setCurrSpeedX(-1 * periodicTranslation.getCurrSpeedX());
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
