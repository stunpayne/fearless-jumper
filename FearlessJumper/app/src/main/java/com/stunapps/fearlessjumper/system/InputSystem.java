package com.stunapps.fearlessjumper.system;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

public class InputSystem implements System
{
    private final ComponentManager componentManager;

    @Inject
    public InputSystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
    {
        //Single player game.
        Set<Entity> entitySet = componentManager.getEntities(Input.class);
        if (!entitySet.isEmpty())
        {
            Entity entity = entitySet.iterator().next();

            //TODO: Buggy type casting.
            OrientationInput input = (OrientationInput) entity.getComponent(Input.class);
            Delta delta = input.getDeltaMovement();
            Transform transform = entity.transform;
            transform.position.x += delta.x;
            transform.position.y += delta.y;
        }
    }
}
