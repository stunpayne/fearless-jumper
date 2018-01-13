package com.stunapps.fearlessjumper.system;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;

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
        Set<Entity> entities = DI.di().getInstance(ComponentManager.class).getEntities(RectCollider.class);



    }

    private boolean isCollision(Entity entity1, Entity entity2)
    {
        float left1 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.left;
        float top1 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.top;
        float right1 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.right;
        float bottom1 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.bottom;

        float left2 = ((RectCollider)entity2.getComponent(RectCollider.class)).rect.left;
        float top2 = ((RectCollider)entity2.getComponent(RectCollider.class)).rect.top;
        float right2 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.right;
        float bottom2 = ((RectCollider)entity1.getComponent(RectCollider.class)).rect.bottom;

        int x1Speed = 0;
        int y1Speed = 0;

        int x2Speed = 0;
        int y2Speed = 0;

//        if(entity1.hashComponent(PhysicsComponent.class)){
//            float left2 = ((PhysicsComponent)entity2.getComponent(PhysicsComponent.class)).rect.left;
//        }
        return false;
    }
}
