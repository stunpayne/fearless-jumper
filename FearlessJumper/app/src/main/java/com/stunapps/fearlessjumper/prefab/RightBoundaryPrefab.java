package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.body.RigidBody;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 14/01/18.
 */

public class RightBoundaryPrefab extends Prefab
{
    public RightBoundaryPrefab()
    {
        int x = Constants.SCREEN_WIDTH + 10;
        int y = (Constants.SCREEN_HEIGHT / 2);
        transform = new Transform(new Transform.Position(x,
                y), new Transform.Rotation(), new Transform.Scale());
        components.add(new RectCollider(new Delta(5, y), , ));
        components.add(new RigidBody());
    }
}
