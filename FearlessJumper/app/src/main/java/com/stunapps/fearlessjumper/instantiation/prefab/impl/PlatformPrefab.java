package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;
import java.util.List;

import static com.stunapps.fearlessjumper.game.Environment.unitX;
import static com.stunapps.fearlessjumper.game.Environment.unitY;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends ComponentPrefab
{
	public PlatformPrefab()
	{
		float width = unitX() * 4;
		float grassHeight = unitY() * 0.5f;
		float soilHeight = grassHeight * 3;

		Shape grass = new RectShape(width, grassHeight,
				new PaintProperties(null, Color.rgb(0, 130, 0), null, null), new Vector2D());
		Shape soil = new RectShape(width, soilHeight,
				new PaintProperties(null, Color.rgb(100, 50, 50), null, null),
				new Vector2D(0, grassHeight));

		List<Shape> platform = new LinkedList<>();
		platform.add(grass);
		platform.add(soil);

		ShapeRenderable shapeRenderable = new ShapeRenderable(platform, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.SOLID));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
				new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f, 7.5f)));
	}
}
