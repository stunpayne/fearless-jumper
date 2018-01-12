package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;

/**
 * Created by anand.verma on 12/01/18.
 */

public class PlayerPrefab extends Prefab
{
    public PlayerPrefab()
    {
        components.add(new SpriteComponent());
        components.add(new RectCollider());
        components.add(new Health(100));
        components.add(new PhysicsComponent());
    }
}
