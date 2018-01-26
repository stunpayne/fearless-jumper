package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.LowerBoundaryComponent;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.Set;

/**
 * Created by sunny.s on 26/01/18.
 */

@Singleton
public class LowerBoundarySystem implements UpdateSystem
{
    private final ComponentManager componentManager;

    private static long lastProcessTime = 0;

    @Inject
    public LowerBoundarySystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        lastProcessTime = System.currentTimeMillis();

        Entity lowerBoundary = componentManager.getEntity(LowerBoundaryComponent.class);

        lowerBoundary.transform.position.y = Cameras.getMainCamera().position.y + Constants
                    .SCREEN_HEIGHT;
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }
}
