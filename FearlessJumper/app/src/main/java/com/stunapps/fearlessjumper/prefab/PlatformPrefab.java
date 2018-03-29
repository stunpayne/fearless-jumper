package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.util.Log;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.helper.RenderLayers;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends ComponentPrefab
{
	public PlatformPrefab()
	{

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 300;
		transform =
				new Transform(new Position(x, y), new Transform.Rotation(), new Transform.Scale());

		Bitmap sprite = Bitmaps.PLATFORM;
		Log.d("PLATFORM_PREFAB", "Width: " + sprite.getWidth() + " Height: " + sprite.getHeight());
		addComponent(new Obstacle());
		addComponent(new Renderable(sprite, Delta.ZERO, sprite.getWidth(),
								  sprite.getHeight(), RenderLayers.PLATFORM));
		addComponent(new RectCollider(Delta.ZERO, sprite.getWidth(), sprite.getHeight(),
										CollisionLayer.SOLID));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
											new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f,
																		  7.5f)));
	}
}
