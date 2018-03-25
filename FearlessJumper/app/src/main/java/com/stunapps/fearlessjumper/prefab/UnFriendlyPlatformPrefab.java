package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.util.Log;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.Vector2D;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterShape;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Velocity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by sunny.s on 10/01/18.
 */

public class UnFriendlyPlatformPrefab extends ComponentPrefab
{
	public UnFriendlyPlatformPrefab()
	{

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 300;
		transform =
				new Transform(new Position(x, y), new Transform.Rotation(), new Transform.Scale());

		Bitmap sprite = Bitmaps.PLATFORM;
		Bitmap fireTexture = Bitmaps.FIRE_TEXTURE;
		Log.d("UnFriendly_PLATFORM",
				"Width: " + sprite.getWidth() + " Height: " + sprite.getHeight());


		addComponent(new Obstacle());
		addComponent(new ContactDamageComponent(2, false,
				new LinkedList<CollisionFace>(Arrays.asList(CollisionFace.HORIZONTAL))));
		addComponent(new Renderable(sprite, Delta.ZERO, sprite.getWidth(), sprite.getHeight()));
		addComponent(new RectCollider(Delta.ZERO, sprite.getWidth(), sprite.getHeight(),
				CollisionLayer.SOLID));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
				new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f, 7.5f)));
		addComponent(new EternalEmitter(
				EmitterConfig.builder().emitterShape(EmitterShape.CONE_DIVERGE).maxParticles(100)
						.particleLife(800).emissionRate(100)
						.positionVar(new Vector2D(sprite.getWidth() / 2, 0)).maxSpeed(2)
						.direction(90).directionVar(10)
						.offset(new Vector2D(sprite.getWidth() / 2, 0))
						.texture(fireTexture).startAsActive().build()));
	}
}
