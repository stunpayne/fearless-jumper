package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 17/01/18.
 */

public class LandPrefab extends Prefab
{
    public LandPrefab()
    {
        components.add(new RectCollider(new Delta(0, 0), Constants.SCREEN_WIDTH, 10));
    }
}
