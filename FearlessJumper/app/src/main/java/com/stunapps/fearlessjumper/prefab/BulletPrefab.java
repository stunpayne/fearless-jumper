package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.model.Delta;
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

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends ComponentPrefab
{
	public BulletPrefab()
	{
		/*
		Bitmap bullet = Bitmaps.BULLET;

		addComponent(new Renderable(bullet, Delta.ZERO, bullet.getWidth(), bullet.getHeight()));
		*/

		addComponent(new PhysicsComponent(Float.MAX_VALUE, new Velocity(scaleX(250f), 0),
				false));

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(new CircleShape(10, new Shape.PaintProperties(null, Color.RED), new Vector2D
				()));

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);
		addComponent(new RectCollider(Delta.ZERO, shapeRenderable.getWidth(), shapeRenderable.getHeight(), true,
				CollisionLayer.BULLET));
		addComponent(new Enemy(EnemyType.GRORUM));
		addComponent(new ContactDamageComponent(10, true));
	}
}
