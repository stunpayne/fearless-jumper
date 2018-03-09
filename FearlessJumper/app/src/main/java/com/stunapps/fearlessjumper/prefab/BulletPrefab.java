package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends Prefab
{
	public BulletPrefab()
	{
		components.add(new PhysicsComponent(0, new Velocity(5f, 0), false));
	}
}
