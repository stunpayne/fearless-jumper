package com.stunapps.fearlessjumper.prefab;

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

/**
 * Created by sunny.s on 09/03/18.
 */

public class BulletPrefab extends ComponentPrefab
{
	public BulletPrefab()
	{
		PaintProperties linePaint = new PaintProperties(null, Color.BLUE, 5.0f, Style.STROKE);
		LinkedList<Shape> shapes = new LinkedList<>();
		float radius = 8f;

		Vector2D vertex1 = new Vector2D(0f, 0f);
		Vector2D vertex2 = new Vector2D(25.0f, 43.0f);
		Vector2D vertex3 = new Vector2D(50.0f, 0.0f);
		Vector2D[] vertices = {vertex1, vertex2, vertex3};
		Vector2D centroid = Triangle.centroid(vertices);

		LineShape line1 = new LineShape(vertex1, vertex2, linePaint);
		LineShape line2 = new LineShape(vertex2, vertex3, linePaint);
		LineShape line3 = new LineShape(vertex1, vertex3, linePaint);
		CircleShape centroidCircle =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null),
						Vector2D.minus(centroid, new Vector2D(radius, radius)));

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
}
