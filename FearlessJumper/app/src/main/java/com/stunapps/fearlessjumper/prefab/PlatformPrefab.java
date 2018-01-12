package com.stunapps.fearlessjumper.prefab;

import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends Prefab
{
    public PlatformPrefab()
    {
        //TODO: To add sprite for platform.
        transform = new Transform(new float[]
                {Constants.SCREEN_WIDTH / 2,
                        (Constants.SCREEN_HEIGHT / 2) - 400}, null, null);
        components.add(new SpriteComponent(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .platform)));
//        components.add(new RectCollider());
    }
}
