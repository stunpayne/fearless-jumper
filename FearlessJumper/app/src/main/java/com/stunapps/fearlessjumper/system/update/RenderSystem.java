package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.helper.Constants.Game;

import java.util.Set;

/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
    private final GameComponentManager componentManager;
    private static long lastProcessTime = System.nanoTime();

    @Inject
    public RenderSystem(GameComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        lastProcessTime = System.currentTimeMillis();
        //  Render all objects at their current positions
        Set<Entity> entities = componentManager.getEntities(RenderableComponent.class);
        Canvas canvas = Constants.canvas;
        if (canvas == null)
            return;
        canvas.drawColor(Color.BLACK);

        for (Entity entity : entities)
        {
            RenderableComponent component = (RenderableComponent) entity.getComponent(
                    RenderableComponent.class);
            switch (component.renderType)
            {
                case SPRITE:
                    Bitmap bitmap = (Bitmap) component.getRenderable();
                    canvas.drawBitmap(bitmap,
                            null,
                            new Rect((int) (entity.transform.position.x + component.delta.x),
                                    (int) (entity.transform.position.y + component.delta.y),
                                    (int) (entity.transform.position.x + component.delta.x +
                                            component.width),
                                    (int) (entity.transform.position.y + component.delta.y
                                            + component.height)),
                            null);
                    break;
                default:
            }
        }
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }
}
