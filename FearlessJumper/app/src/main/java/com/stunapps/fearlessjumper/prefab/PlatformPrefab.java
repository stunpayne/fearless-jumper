package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.body.RigidBody;
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
        int x = Constants.SCREEN_WIDTH / 2;
        int y = (Constants.SCREEN_HEIGHT / 2) - 400;
        transform = new Transform(new Transform.Position(x,
                y), new Transform.Rotation(), new Transform.Scale());
        Bitmap sprite = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .platform);
        components.add(new SpriteComponent(sprite, new Delta(sprite.getWidth() / 2, sprite.getHeight() / 2)));
        components.add(new RectCollider(new Delta(sprite.getWidth() / 2, sprite.getHeight() / 2)));
        components.add(new RigidBody());
    }
}
