package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
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

        int x = 4 * Constants.SCREEN_WIDTH / 5;
        int y = Constants.SCREEN_HEIGHT - 150;
        transform = new Transform(new Position(x,
                y), new Transform.Rotation(), new Transform.Scale());
        Bitmap sprite = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue);
        components.add(new SpriteComponent(sprite, new Delta(0, 0), sprite.getWidth(), sprite.getHeight()));
        components.add(new RectCollider(new Delta(0, 0), sprite.getWidth(), sprite.getHeight()));
        components.add(new Health(100));
        components.add(new PhysicsComponent(50, new PhysicsComponent.Velocity()));
        OrientationInput orientationInput = DI.di().getInstance(OrientationInput.class);
        components.add(orientationInput);
        components.add(new PlayerComponent());

    }
}
