package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by sunny.s on 20/01/18.
 */

public class MidScreenColliderPrefab extends Prefab
{
    public MidScreenColliderPrefab()
    {
        transform = new Transform(new Position(0, Constants.SCREEN_HEIGHT / 2 - 10));
        
        RectCollider rectCollider = new RectCollider(new Delta(0, 0), Constants.SCREEN_WIDTH, 10);
        components.add(rectCollider);
    }
}
