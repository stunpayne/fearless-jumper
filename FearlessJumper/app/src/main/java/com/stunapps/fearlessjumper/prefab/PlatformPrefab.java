package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends Prefab
{
    public PlatformPrefab()
    {
        //TODO: To add sprite for platform.
        components.add(new SpriteComponent(null));
//        components.add(new RectCollider());
    }
}
