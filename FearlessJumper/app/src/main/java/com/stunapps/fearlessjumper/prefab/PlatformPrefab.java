package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends ComponentPrefab
{
	public PlatformPrefab()
	{

		/*int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 300;
		transform =
				new Transform(new Position(x, y), new Transform.Rotation(), new Transform.Scale());

		Bitmap sprite = Bitmaps.PLATFORM;
		Log.d("PLATFORM_PREFAB", "Width: " + sprite.getWidth() + " Height: " + sprite.getHeight());
		addComponent(new Obstacle());
		addComponent(new Renderable(sprite, Vector2D.ZERO, sprite.getWidth(),
								  sprite.getHeight()));*/


		Shape grass = new RectShape(150, 20, new PaintProperties(null, Color.rgb(0, 130, 0)
				, null,
																 null),
									new Vector2D());
		Shape soil =
				new RectShape(150, 60, new PaintProperties(null, Color.rgb(100, 50, 50),
														   null,
															null),
							  new Vector2D(0, 20));

		List<Shape> platform = new LinkedList<>();
		platform.add(grass);
		platform.add(soil);

		ShapeRenderable shapeRenderable = new ShapeRenderable(platform, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(), shapeRenderable.getHeight(),
										CollisionLayer.SOLID));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false,
											new PhysicsComponent.Friction(50.0f, 7.5f, 7.5f,
																		  7.5f)));
	}
}
