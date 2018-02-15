package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.LowerBoundaryComponent;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Environment.Device;

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

        //  TODO: We directly set the lower boundary to the magnitude of screen height below
        //  the camera position. So, we are assuming that the camera will always be on the top.
        //  We need to get rid of this assumption by storing the offset of the lower boundary
        //  from the initial camera position.
        Entity lowerBoundary = componentManager.getEntity(LowerBoundaryComponent.class);

        lowerBoundary.transform.position.y = Cameras.getMainCamera().position.y + Device
                .SCREEN_HEIGHT;
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }
}
