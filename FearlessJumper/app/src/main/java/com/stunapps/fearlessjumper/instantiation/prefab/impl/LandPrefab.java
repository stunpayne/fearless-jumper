package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.LowerBoundaryComponent;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by anand.verma on 17/01/18.
 */

public class LandPrefab extends ComponentPrefab
{
    public LandPrefab()
    {
        addComponent(new RectCollider(Vector2D.ZERO, Device.SCREEN_WIDTH, 10, CollisionLayer.LAND));
        addComponent(new LowerBoundaryComponent());
        addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
											new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f,
                                                                          7.5f)));
    }
}
