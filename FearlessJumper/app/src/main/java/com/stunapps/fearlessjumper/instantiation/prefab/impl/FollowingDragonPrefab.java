package com.stunapps.fearlessjumper.instantiation.prefab.impl;


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

import static com.stunapps.fearlessjumper.game.Environment.unitX;

/**
 * Created by anand.verma on 10/03/18.
 */

public class FollowingDragonPrefab extends ComponentPrefab
{
	public FollowingDragonPrefab()
	{
		LinkedList<Shape> shapes = new LinkedList<>();
		float bladeLength = unitX() * 0.35f;
		float radius = bladeLength;

		CircleShape circleShape =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null),
						new Vector2D(bladeLength, bladeLength));
		shapes.add(circleShape);

		PaintProperties linePaintProperties =
				new PaintProperties(null, Color.GRAY, 4.0f, Style.STROKE);
		LineShape lineShapeLeft = new LineShape(0, circleShape.getTop() + radius, bladeLength,
				circleShape.getTop() + radius, linePaintProperties);
		shapes.add(lineShapeLeft);

		LineShape lineShapeRight =
				new LineShape(circleShape.getRight(), circleShape.getTop() + radius,
						circleShape.getRight() + bladeLength, circleShape.getTop() + radius,
						linePaintProperties);
		shapes.add(lineShapeRight);

		LineShape lineShapeTop =
				new LineShape(circleShape.getLeft() + radius, 0, circleShape.getLeft() + radius,
						circleShape.getTop(), linePaintProperties);
		shapes.add(lineShapeTop);

		LineShape lineShapeBottom =
				new LineShape(circleShape.getLeft() + radius, circleShape.getBottom(),
						circleShape.getLeft() + radius, circleShape.getBottom() + bladeLength,
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
