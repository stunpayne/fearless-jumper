package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.ComponentPrefab;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anand.verma on 10/05/18.
 */

public class RevolvingEnemyPrefab extends ComponentPrefab
{
	private static final float INVISIBLE_SHAPE_SIZE = 100.0f;

	private static final PaintProperties INIVISIBLE_PAINT =
			new PaintProperties(null, Color.TRANSPARENT, null, null);

	private static final PaintProperties RED_PAINT =
			new PaintProperties(null, Color.RED, null, null);

	public RevolvingEnemyPrefab()
	{
		Vector2D delta = new Vector2D();
		Vector2D startPoint = new Vector2D();
		Vector2D endPoint = new Vector2D(0, INVISIBLE_SHAPE_SIZE);
		LineShape invisibleShape =
				new LineShape(startPoint.getX(), startPoint.getY(), endPoint.getX(),
							  endPoint.getY(), INIVISIBLE_PAINT);

		float radius = 5.0f;
		CircleShape enemy = new CircleShape(radius, RED_PAINT, delta);

		List<Shape> shapeList = new LinkedList<>();
		shapeList.add(invisibleShape);
		shapeList.add(enemy);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapeList, delta);

		addComponent(shapeRenderable);

		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
									  shapeRenderable.getHeight(), CollisionLayer.ENEMY));

		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, 900.0f, false));
	}
}
