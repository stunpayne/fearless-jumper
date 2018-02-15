package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.specific.LowerBoundaryComponent;
import com.stunapps.fearlessjumper.helper.Environment.Device;

/**
 * Created by anand.verma on 17/01/18.
 */

public class LandPrefab extends Prefab
{
    public LandPrefab()
    {
        components.add(new RectCollider(Delta.ZERO, Device.SCREEN_WIDTH, 10));
        components.add(new LowerBoundaryComponent());
    }
}
