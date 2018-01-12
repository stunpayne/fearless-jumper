package com.stunapps.fearlessjumper.prefab;

import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 12/01/18.
 */

public class PlayerPrefab extends Prefab
{
    public PlayerPrefab()
    {
        transform = new Transform(new float[]
                {Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2}, null, null);
        components.add(new Health(100));
        components.add(new PhysicsComponent(50, 0));
        components.add(new SpriteComponent(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue)));
    }
}
