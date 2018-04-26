package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PeriodicGun;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by anand.verma on 11/04/18 10:12 PM.
 */

public class TestShapePrefab extends ComponentPrefab
{
	public TestShapePrefab()
	{
		transform = new Transform(
				new Position(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2 + scaleY(100)));
		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(new CircleShape(100, new Shape.PaintProperties(null, Color.BLUE, null, null),
				new Vector2D(10, 10)));
		shapes.add(new RectShape(100, 200, new Shape.PaintProperties(null, Color.RED, null, null),
				new Vector2D(10, 10)));

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new PeriodicTranslation().withAnchoredYMovement(100f, scaleY(100f)));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false));
		addComponent(new Dragon(EnemyType.GRORUM));
		addComponent(new PeriodicGun(2000));
	}
}
