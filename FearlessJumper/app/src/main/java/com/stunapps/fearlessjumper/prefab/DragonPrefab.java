package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.MoveDownComponent;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by sunny.s on 21/01/18.
 */

public class DragonPrefab extends Prefab
{
    public DragonPrefab()
    {
        transform = new Transform(new Position(Constants.SCREEN_WIDTH / 2, 200));

        Bitmap dragonSprite = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT
                .getResources(), R.drawable.dragon1);
        components.add(new SpriteComponent(dragonSprite, new Delta(0, 0), dragonSprite.getWidth(),
                dragonSprite.getHeight()));
        components.add(new RectCollider(new Delta(0, 0), dragonSprite.getWidth(),
                dragonSprite.getHeight()));
        components.add(new PeriodicTranslation()
                .withXMovement(0, Constants.SCREEN_WIDTH - dragonSprite.getWidth(), 5f));
        components.add(new MoveDownComponent());
    }
}
