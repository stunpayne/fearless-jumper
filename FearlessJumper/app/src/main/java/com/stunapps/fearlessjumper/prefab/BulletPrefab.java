package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends ComponentPrefab
{
	public BulletPrefab()
	{
		Bitmap bullet = Bitmaps.BULLET;

		addComponent(new Renderable(bullet, Vector2D.ZERO, bullet.getWidth(), bullet.getHeight()));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, new Velocity(scaleX(250f), 0),
				false));
		addComponent(new RectCollider(Vector2D.ZERO, bullet.getWidth(), bullet.getHeight(), true,
				CollisionLayer.BULLET));
		addComponent(new Enemy(EnemyType.GRORUM));
		addComponent(new ContactDamageComponent(10, true));
	}
}
