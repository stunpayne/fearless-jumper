package com.stunapps.fearlessjumper.system.input;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;

import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class GameInputSystem implements InputSystem
{
    private final ComponentManager componentManager;
    private final InputProcessorFactory inputProcessorFactory;

    @Inject
    public GameInputSystem(ComponentManager componentManager,
                           InputProcessorFactory inputProcessorFactory)
    {
        this.componentManager = componentManager;
        this.inputProcessorFactory = inputProcessorFactory;
    }

    @Override
    public void processInput(MotionEvent motionEvent)
    {
        //Single player game.
        Set<Entity> entitySet = componentManager.getEntities(Input.class);
        if (!entitySet.isEmpty())
        {
            Entity entity = entitySet.iterator().next();

            if (entity.hasComponent(PlayerComponent.class))
            {
                //  The entity has a player component
                inputProcessorFactory.get(PlayerComponent.class).process(entity, motionEvent);
            }
        }
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
