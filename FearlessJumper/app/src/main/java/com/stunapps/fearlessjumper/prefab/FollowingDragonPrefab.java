package com.stunapps.fearlessjumper.prefab;


import android.graphics.Color;
import android.graphics.Paint.Style;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.FollowTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;

import java.util.LinkedList;

/**
 * Created by anand.verma on 10/03/18.
 */

public class FollowingDragonPrefab extends ComponentPrefab
{
	public FollowingDragonPrefab()
	{
		LinkedList<Shape> shapes = new LinkedList<>();
		CircleShape circleShape =
				new CircleShape(15, new PaintProperties(null, Color.BLACK, null, null),
								new
						Vector2D(15,
																							 15));
		shapes.add(circleShape);

		PaintProperties linePaintProperties = new PaintProperties(null, Color.GRAY,
																  4.0f, Style
				.STROKE);
		LineShape lineShapeLeft =
				new LineShape(0, circleShape.getTop() + circleShape.getRadius(), 15,
							  circleShape.getTop() + circleShape.getRadius(),
							  linePaintProperties);
		shapes.add(lineShapeLeft);

		LineShape lineShapeRight = new LineShape(circleShape.getRight(),
												 circleShape.getTop() + circleShape.getRadius(),
												 circleShape.getRight() + 15,
												 circleShape.getTop() + circleShape.getRadius(),
												 linePaintProperties);
		shapes.add(lineShapeRight);

		LineShape lineShapeTop = new LineShape(circleShape.getLeft() + circleShape.getRadius(), 0,
											   circleShape.getLeft() + circleShape.getRadius(),
											   circleShape.getTop(),
											   linePaintProperties);
		shapes.add(lineShapeTop);

		LineShape lineShapeBottom = new LineShape(circleShape.getLeft() + circleShape.getRadius(),
												  circleShape.getBottom(),
												  circleShape.getLeft() + circleShape.getRadius(),
												  circleShape.getBottom() + 15,
												  linePaintProperties);
		shapes.add(lineShapeBottom);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new Dragon(EnemyType.ZELDROY));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
									  shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new FollowTranslation(PlayerComponent.class, 150));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(false));
	}

}
