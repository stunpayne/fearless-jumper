package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends Prefab
{
    public PlatformPrefab()
    {
        components.add(new SpriteComponent());
        components.add(new RectCollider());
        components.add(new ContactDamageComponent());
    }
}
