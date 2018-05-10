package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Color;
import android.graphics.Paint.Style;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.helper.Triangle;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.unitX;

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends ComponentPrefab
{
	public BulletPrefab()
	{
		float radius = unitX() / 7f;
		float triangleSideLength = unitX();
		float triangleTopHeight = triangleTopHeight(triangleSideLength);

		Vector2D vertex1 = new Vector2D(0f, 0f);
		Vector2D vertex2 = new Vector2D(triangleSideLength / 2, triangleTopHeight);
		Vector2D vertex3 = new Vector2D(triangleSideLength, 0.0f);
		Vector2D[] vertices = {vertex1, vertex2, vertex3};
		Vector2D centroid = Triangle.centroid(vertices);

		PaintProperties linePaint = new PaintProperties(null, Color.BLUE, 5.0f, Style.STROKE);
		LineShape line1 = new LineShape(vertex1, vertex2, linePaint);
		LineShape line2 = new LineShape(vertex2, vertex3, linePaint);
		LineShape line3 = new LineShape(vertex1, vertex3, linePaint);
		CircleShape centroidCircle =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null),
						Vector2D.minus(centroid, new Vector2D(radius, radius)));

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(line1);
		shapes.add(line2);
		shapes.add(line3);
		shapes.add(centroidCircle);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D(), centroid);
		addComponent(shapeRenderable);
		addComponent(new PhysicsComponent(Float.MAX_VALUE, new Velocity(scaleX(180f), 0), 1200.0f,
				false));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), true, CollisionLayer.BULLET));
		addComponent(new Enemy(EnemyType.GRORUM));
		addComponent(new ContactDamageComponent(10, true));
	}

	private float triangleTopHeight(float triangleSideLength)
	{
		return triangleSideLength * (float) Math.sin(Math.toRadians(60));
	}
}
