package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Obstacle;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends Prefab
{
	public BulletPrefab()
	{
		Bitmap bullet = Bitmaps.BULLET;

		components.add(new Renderable(bullet, Delta.ZERO, bullet.getWidth(), bullet.getHeight()));
		components.add(new PhysicsComponent(Float.MAX_VALUE, new Velocity(5f, 0), false));
		components.add(new RectCollider(Delta.ZERO, bullet.getWidth(), bullet.getHeight(), true,
				CollisionLayer.BULLET));
		components.add(new Obstacle());
		components.add(new ContactDamageComponent(10));
	}
}
