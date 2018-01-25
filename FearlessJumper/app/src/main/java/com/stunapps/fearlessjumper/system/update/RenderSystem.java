package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.display.Cameras;
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
    private static Canvas canvas = null;

    @Inject
    public RenderSystem(GameComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        lastProcessTime = System.currentTimeMillis();

        Set<Entity> entities = componentManager.getEntities(RenderableComponent.class);
        if (canvas == null)
        {
            canvas = Constants.canvas;
        }
        canvas.drawColor(Color.BLACK);

        //  Update all cameras
        Cameras.update();

        //  Render all objects at their current positions
        for (Entity entity : entities)
        {
            RenderableComponent component = (RenderableComponent) entity.getComponent(
                    RenderableComponent.class);
            switch (component.renderType)
            {
                case SPRITE:
                    Bitmap bitmap = (Bitmap) component.getRenderable();
                    Rect destRect = getRenderRect(entity);

                    canvas.drawBitmap(bitmap, null, destRect, null);
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

    public static Rect getRenderRect(Entity entity)
    {
        Position camPosition = Cameras.getMainCamera().position;

        RenderableComponent component = (RenderableComponent) entity.getComponent(
                RenderableComponent.class);
        int left = (int) ((entity.transform.position.x + component.delta.x) - camPosition.x);
        int top = (int) ((entity.transform.position.y + component.delta.y) - camPosition.y);
        int right = (int) ((entity.transform.position.x + component.delta.x +
                component.width) - camPosition.x);
        int bottom = (int) ((entity.transform.position.y + component.delta
                .y + component.height) - camPosition.y);

        return new Rect(left, top, right, bottom);
    }
}
