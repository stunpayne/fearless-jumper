package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 12/01/18.
 */

public class PlayerPrefab extends Prefab
{
    public PlayerPrefab()
    {

        int x = Constants.SCREEN_WIDTH / 4;
        int y = (Constants.SCREEN_HEIGHT / 2) - 400;
        transform = new Transform(new Transform.Position(x,
                y), new Transform.Rotation(), new Transform.Scale());
        Bitmap sprite = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue);
        components.add(new SpriteComponent(sprite, new Delta(sprite.getWidth() / 2, sprite.getHeight() / 2)));
        components.add(new RectCollider(new Delta(sprite.getWidth() / 2, sprite.getHeight() / 2), , ));
        components.add(new Health(100));
        components.add(new PhysicsComponent(50, new PhysicsComponent.Velocity()));
        OrientationInput orientationInput = DI.di().getInstance(OrientationInput.class);
        components.add(orientationInput);

    }
}
