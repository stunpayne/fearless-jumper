package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 14/01/18.
 */

public class BoundaryPrefab extends Prefab
{
    public BoundaryPrefab()
    {
        components.add(new RectCollider(new Delta(0, 0), 10, Constants.SCREEN_HEIGHT));
    }
}
