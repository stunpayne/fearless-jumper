package com.stunapps.fearlessjumper.system;


import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.EntityManager;

/**
 * Created by sunny.s on 03/01/18.
 */

public class CollisionSystem implements System
{
    @Override
    public void process()
    {
        //  Get transforms of all platforms in game and check for collision with player -- ?
        //  Get transforms of all enemies in game and check for collision with player
        DI.di().getInstance(ComponentManager.class).getEntities(Collider.class);
    }
}
