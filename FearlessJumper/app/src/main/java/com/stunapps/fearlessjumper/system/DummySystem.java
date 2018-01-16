package com.stunapps.fearlessjumper.system;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.Set;

/**
 * Created by sunny.s on 14/01/18.
 */

public class DummySystem implements System
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;

    private final int deltaY = 1;

    @Inject
    public DummySystem(EntityManager entityManager, ComponentManager componentManager)
    {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
    }

    @Override
    public void process()
    {
        Set<Entity> players = componentManager.getEntities(PlayerComponent.class);

        for (Entity player : players)
        {
            if (player.transform.position.y < Constants.SCREEN_HEIGHT / 2)
                player.transform.position.y += 200;
            player.transform.position.y -= deltaY;
        }
    }
}
