package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.ObstacleComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends Prefab
{
	public PlatformPrefab()
	{
		components.add(new ObstacleComponent());

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 300;
		transform =
				new Transform(new Position(x, y), new Transform.Rotation(), new Transform.Scale());

		Bitmap sprite = BitmapFactory.decodeResource(Environment.CONTEXT.getResources(),
													 R.drawable.platform);
		components.add(new SpriteComponent(sprite, new Delta(0, 0), sprite.getWidth(),
										   sprite.getHeight()));
		components.add(new RectCollider(new Delta(0, 0), sprite.getWidth(), sprite.getHeight()));
		components.add(new PhysicsComponent(Float.MAX_VALUE, new PhysicsComponent.Velocity(), false,
											new PhysicsComponent.Friction(7.5f, 7.5f, 7.5f, 7.5f)));
	}
}
