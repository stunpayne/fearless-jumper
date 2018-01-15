package com.stunapps.fearlessjumper.system;

import android.graphics.Rect;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.HashSet;
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

        Set<Entity> movableEntities = new HashSet<>();
        Set<Entity> nonMovableEntities = new HashSet<>();

        for (Entity entity : entities)
        {
            if (entity.hasComponent(PhysicsComponent.class))
            {
                movableEntities.add(entity);
            } else
            {
                nonMovableEntities.add(entity);
            }
        }

        //TODO: Collision logic.
        for (Entity movableEntity : movableEntities)
        {
            for (Entity nonMovableEntity : nonMovableEntities)
            {
                if (isColliding(movableEntity, nonMovableEntity))
                {
                    //TODO: Handle collision.
                    
                }
            }

            for (Entity movableEntity1 : movableEntities)
            {
                //TODO: Bug, Two different entities will be processed for collision twice here.
                if (!movableEntity.equals(movableEntity1))
                {
                    if (isColliding(movableEntity, movableEntity1))
                    {
                        //TODO: handle collision.
                    }
                }
            }
        }
    }

    private boolean isColliding(Entity entity1, Entity entity2)
    {
        int left1 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.left;
        int top1 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.top;
        int right1 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.right;
        int bottom1 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.bottom;

        int left2 = ((RectCollider) entity2.getComponent(RectCollider.class)).rect.left;
        int top2 = ((RectCollider) entity2.getComponent(RectCollider.class)).rect.top;
        int right2 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.right;
        int bottom2 = ((RectCollider) entity1.getComponent(RectCollider.class)).rect.bottom;

        float x1Speed = 0;
        float y1Speed = 0;

        float x2Speed = 0;
        float y2Speed = 0;

        if (entity1.hasComponent(PhysicsComponent.class))
        {
            x1Speed = ((PhysicsComponent) entity1.getComponent(PhysicsComponent.class)).velocity.x;
            y1Speed = ((PhysicsComponent) entity1.getComponent(PhysicsComponent.class)).velocity.y;
        }

        if (entity2.hasComponent(PhysicsComponent.class))
        {
            x2Speed = ((PhysicsComponent) entity2.getComponent(PhysicsComponent.class)).velocity.x;
            y2Speed = ((PhysicsComponent) entity2.getComponent(PhysicsComponent.class)).velocity.y;
        }

        //Update x and y co-ordinates to guess next positions of entities.
        left1 += x1Speed;
        right1 += x1Speed;
        top1 += y1Speed;
        bottom1 += y1Speed;

        left2 += x2Speed;
        right2 += x2Speed;
        top2 += y2Speed;
        bottom2 += y2Speed;

        return Rect.intersects(new Rect(left1, top1, right1, bottom1), new Rect(left2, top2, right2, bottom2));
    }
}
