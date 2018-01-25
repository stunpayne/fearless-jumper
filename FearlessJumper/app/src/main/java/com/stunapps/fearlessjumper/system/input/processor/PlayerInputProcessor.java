package com.stunapps.fearlessjumper.system.input.processor;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.Systems;
import com.stunapps.fearlessjumper.system.update.RenderSystem;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.stunapps.fearlessjumper.helper.Constants.scale;

/**
 * Created by sunny.s on 20/01/18.
 */

@Singleton
public class PlayerInputProcessor implements InputProcessor
{
    private static float JUMP_IMPULSE = 1 / 80f;
    private static long lastProcessTime = System.nanoTime();

    private static Map<Class, Long> debugSystemRunTimes = new HashMap<>();

    @Inject
    public PlayerInputProcessor()
    {
    }

    @Override
    public void process(Entity entity, MotionEvent motionEvent)
    {
        lastProcessTime = System.currentTimeMillis();
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            //  To test is input is occurring between collision and transform update
            logSystemTimes();
            Log.d("INPUT", "Action Down detected");
            Rect entityCanvasRect = RenderSystem.getRenderRect(entity);
            applyForceToPlayer(new Position(entityCanvasRect.left, entityCanvasRect.top),
                    (PhysicsComponent) entity.getComponent(PhysicsComponent.class),
                    motionEvent);
        }
    }

    private void applyForceToPlayer(Position position, PhysicsComponent physicsComponent,
                                    MotionEvent motionEvent)
    {
        float forceX = motionEvent.getX() - position.x;
        float forceY = motionEvent.getY() - position.y;

        Log.d("INPUT", "Velocity before " + physicsComponent.velocity.x + " " +
                physicsComponent.velocity.y);
        physicsComponent.velocity.y += forceY * JUMP_IMPULSE * scale();
        physicsComponent.velocity.x += forceX * JUMP_IMPULSE * scale();
        Log.d("INPUT", "Velocity after " + physicsComponent.velocity.x + " " + physicsComponent
                .velocity.y);
    }


    /**
     * Helper methods to log the last times at which various systems ran. Used for some debugging
     */
    private void logSystemTimes()
    {
        debugSystemRunTimes.put(this.getClass(), this.lastProcessTime);
        for (UpdateSystem system : Systems.getSystemsInOrder())
        {
            debugSystemRunTimes.put(system.getClass(), system.getLastProcessTime());
        }

        List<Entry<Class, Long>> list =
                new LinkedList<Entry<Class, Long>>(debugSystemRunTimes.entrySet());

        Collections.sort(list, comparator());
        for (Entry<Class, Long> entry : list)
        {
            Log.v("SYSTEM", "System\t" + entry.getKey().getSimpleName() + "\t" +
                    entry.getValue());
        }
    }

    private static Comparator<Map.Entry<Class, Long>> comparator()
    {
        return new Comparator<Map.Entry<Class, Long>>()
        {
            @Override
            public int compare(Map.Entry<Class, Long> o1, Map.Entry<Class, Long> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        };
    }
}
