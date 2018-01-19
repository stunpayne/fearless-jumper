package com.stunapps.fearlessjumper.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements System
{
    private final GameComponentManager componentManager;

    @Inject
    public RenderSystem(GameComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        //  Render all objects at their current positions
        Set<Entity> entities = componentManager.getEntities(RenderableComponent.class);
        Canvas canvas = Constants.canvas;
        canvas.drawColor(Color.BLACK);
        
        for (Entity entity : entities)
        {
            RenderableComponent component = (RenderableComponent) entity.getComponent(RenderableComponent.class);
            switch (component.renderType)
            {
                case SPRITE:
                    Bitmap bitmap = (Bitmap) component.getRenderable();
                    canvas.drawBitmap(bitmap,
                            null,
                            new Rect((int) entity.transform.position.x + (int) component.delta.x, (int) entity
                                    .transform.position.y + (int) component.delta.y, (int) entity.transform.position.x + (int) component.delta.x + (int)component.width
                                    , (int) entity.transform.position.y + (int) component.delta.y + (int)component.height),
                            null);
                    break;
                default:
            }
        }
    }
}
