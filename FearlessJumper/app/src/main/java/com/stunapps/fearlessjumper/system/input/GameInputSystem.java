package com.stunapps.fearlessjumper.system.input;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

public class GameInputSystem implements InputSystem
{
    private final ComponentManager componentManager;

    @Inject
    public GameInputSystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void processInput(MotionEvent motionEvent)
    {
        //Single player game.
        Set<Entity> entitySet = componentManager.getEntities(Input.class);
        if (!entitySet.isEmpty())
        {
            Entity entity = entitySet.iterator().next();

            if (entity.getComponent(PlayerComponent.class) != null)
            {
                //  The entity has a player component
                processPlayerInput(entity, motionEvent);
            }
        }
    }

    private void processPlayerInput(Entity player, MotionEvent event)
    {

    }

    private void processOrientationInput(Entity entity)
    {
        OrientationInput input = (OrientationInput) entity.getComponent(Input.class);
        Delta delta = input.getDeltaMovement();
        Transform transform = entity.transform;
        transform.position.x += delta.x;
        transform.position.y += delta.y;
    }
}
