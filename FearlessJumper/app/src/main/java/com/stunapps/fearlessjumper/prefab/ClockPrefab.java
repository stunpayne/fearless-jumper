package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.Pickup.PickupType;
import com.stunapps.fearlessjumper.component.visual.Sprite;
import com.stunapps.fearlessjumper.helper.Environment;

/**
 * Created by sunny.s on 15/02/18.
 */

public class ClockPrefab extends Prefab
{
	public ClockPrefab()
	{
		Bitmap clockSprite =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), R.drawable.clock);
		float width = 100;
		float height = 100;
		components.add(new Pickup(PickupType.CLOCK, 10f));
		components.add(new RectCollider(Delta.ZERO, width, height, true));
		components.add(new Sprite(clockSprite, Delta.ZERO, width, height));
	}
}
