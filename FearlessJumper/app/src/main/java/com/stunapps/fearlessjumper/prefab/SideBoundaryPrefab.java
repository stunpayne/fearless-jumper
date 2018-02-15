package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.helper.Environment.Device;

/**
 * Created by anand.verma on 14/01/18.
 */

public class SideBoundaryPrefab extends Prefab
{
    public SideBoundaryPrefab()
    {
        components.add(new RectCollider(new Delta(0, 0), 10, Device.SCREEN_HEIGHT));
    }
}
