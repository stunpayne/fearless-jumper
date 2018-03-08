package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.Pickup.PickupType;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.manager.CollisionLayer;

/**
 * Created by sunny.s on 15/02/18.
 */

public class ClockPrefab extends Prefab
{
	public ClockPrefab()
	{
		Bitmap clockSprite = Bitmaps.CLOCK;
		float width = clockSprite.getWidth();
		float height = clockSprite.getHeight();
		components.add(new Pickup(PickupType.CLOCK, 5000f));
		components.add(new RectCollider(Delta.ZERO, width, height, true, CollisionLayer.BONUS));
		components.add(new Renderable(clockSprite, Delta.ZERO, width, height));
	}
}
