package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;


import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterShape;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.stunapps.fearlessjumper.game.Environment.unitX;

/**
 * Created by sunny.s on 10/01/18.
 */

public class UnfriendlyPlatformPrefab extends ComponentPrefab
{
	public UnfriendlyPlatformPrefab()
	{
		float width = unitX() * 3f;
		float burntHeight = 1f;
		float grassHeight = unitX() * 0.3f;
		float soilHeight = 3 * grassHeight;

		addComponent(new Obstacle());
		addComponent(new ContactDamageComponent(2, false,
				new LinkedList<CollisionFace>(Arrays.asList(CollisionFace.HORIZONTAL))));

		Shape burnt = new RectShape(width, burntHeight,
				new PaintProperties(null, Color.rgb(0, 0, 0), null, null), new Vector2D());
		Shape grass = new RectShape(width, grassHeight,
				new PaintProperties(null, Color.rgb(0, 130, 0), null, null),
				new Vector2D(0, burntHeight));
		Shape soil = new RectShape(width, soilHeight,
				new PaintProperties(null, Color.rgb(100, 50, 50), null, null),
				new Vector2D(0, burntHeight + grassHeight));

		List<Shape> platform = new LinkedList<>();
		platform.add(burnt);
		platform.add(grass);
		platform.add(soil);

		ShapeRenderable shapeRenderable = new ShapeRenderable(platform, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.SOLID));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
				new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f, 7.5f)));

		Bitmap fireTexture = Bitmaps.FIRE_TEXTURE;
		addComponent(new EternalEmitter(
				EmitterConfig.builder().emitterShape(EmitterShape.CONE_DIVERGE).maxParticles(100)
						.particleLife(800).emissionRate(100)
						.positionVar(new Vector2D(shapeRenderable.getWidth() / 2, 0)).maxSpeed(2)
						.direction(90).directionVar(10)
						.offset(new Vector2D(shapeRenderable.getWidth() / 2, 0))
						.texture(fireTexture).blendingMode(Mode.ADD).startAsActive().build()));
	}
}
