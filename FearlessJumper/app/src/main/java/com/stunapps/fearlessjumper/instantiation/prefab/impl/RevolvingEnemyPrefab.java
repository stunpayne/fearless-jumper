package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Color;
import android.graphics.Paint.Style;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.movement.RevolvingTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.unitX;

/**
 * Created by anand.verma on 17/05/18 8:08 PM.
 */

public class RevolvingEnemyPrefab extends ComponentPrefab
{
	public RevolvingEnemyPrefab()
	{
		float bladeLength = unitX() * 0.3f;
		float radius = 1.4f * bladeLength;

		CircleShape circle =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null),
								new Vector2D(bladeLength, bladeLength));

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(circle);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new Dragon(EnemyType.MINIGON));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
									  shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new RevolvingTranslation(20.0f, 10.0f));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, new Velocity(0.00f, 0.00f), 0.0f,
										  false));
	}
}
