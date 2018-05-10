package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.spawnable.Pickup;
import com.stunapps.fearlessjumper.component.spawnable.Pickup.PickupType;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 15/02/18.
 */

public class ClockPrefab extends ComponentPrefab
{
	public ClockPrefab()
	{
		Bitmap clockSprite = Bitmaps.CLOCK;
		float width = clockSprite.getWidth();
		float height = clockSprite.getHeight();
		addComponent(new Pickup(PickupType.CLOCK, 5000f));
		addComponent(new RectCollider(Vector2D.ZERO, width, height, true, CollisionLayer.BONUS));
		addComponent(new Renderable(clockSprite, Vector2D.ZERO, width, height));
	}
}
