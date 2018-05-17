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
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.unitX;

/**
 * Created by sunny.s on 21/01/18.
 */

public class RotatingWheelPrefab extends ComponentPrefab
{
	public RotatingWheelPrefab()
	{
		float bladeLength = unitX() * 0.9f;
		float radius = 2f * bladeLength;

		CircleShape circle =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null),
						new Vector2D(bladeLength, bladeLength));

		PaintProperties linePaintProperties =
				new PaintProperties(null, Color.GRAY, 8.0f, Style.STROKE);
		LineShape horizontalLine =
				new LineShape(0, circle.getCenter().getY(), 2 * bladeLength + circle.getDiameter(),
						circle.getCenter().getY(), linePaintProperties);

		LineShape verticalLine =
				new LineShape(circle.getCenter().getX(), 0, circle.getCenter().getX(),
						2 * bladeLength + circle.getDiameter(), linePaintProperties);

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(horizontalLine);
		shapes.add(verticalLine);
		shapes.add(circle);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());

		addComponent(shapeRenderable);
		addComponent(new Dragon(EnemyType.MINIGON));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new PeriodicTranslation()
				.withAbsoluteXMovement(0, Device.SCREEN_WIDTH - shapeRenderable.getWidth(), 250f));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, 500.0f, false));
	}
}