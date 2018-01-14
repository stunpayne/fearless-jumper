package com.stunapps.fearlessjumper.system;

import com.stunapps.fearlessjumper.component.transform.Transform;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.body.RigidBody;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.transform.Transform;

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

    private final GameComponentManager componentManager;

    @Inject
    public CollisionSystem(GameComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process()
    {
        //  Get transforms of all platforms in game and check for collision with player -- ?
        //  Get transforms of all enemies in game and check for collision with player
        Set<Entity> entities = componentManager.getEntities(Collider.class);

        Set<Entity> movableEntities = new HashSet<>();
        Set<Entity> rigidEntities = new HashSet<>();

        for (Entity entity : entities)
        {
            if (entity.hashComponent(RigidBody.class))
            {
                rigidEntities.add(entity);
            } else
            {
                movableEntities.add(entity);
            }
        }

        //TODO: Collision logic.
        for (Entity movableEntity : movableEntities)
        {
            for (Entity rigidEntity : rigidEntities)
            {
                if (isCollidingV2(movableEntity, rigidEntity, -0.0f))
                {
                    //TODO: Handle collision.

                }
            }

            for (Entity movableEntity1 : movableEntities)
            {
                //TODO: Bug, Two different entities will be processed for collision twice here.
                if (!movableEntity.equals(movableEntity1))
                {
                    if (isCollidingV2(movableEntity, movableEntity1, 0.0f))
                    {
                        //TODO: handle collision.
                    }
                }
            }
        }
    }

    /*private boolean isColliding(Entity entity1, Entity entity2)
    {
        int left1 = entity1.transform.position.x - ((RectCollider) entity1.getComponent(Collider.class)).delta.x;
        int top1 = entity1.transform.position.y - ((RectCollider) entity1.getComponent(Collider.class)).delta.y;
        int right1 = entity1.transform.position.x + ((RectCollider) entity1.getComponent(Collider.class)).delta.x;
        int bottom1 = entity1.transform.position.y + ((RectCollider) entity1.getComponent(Collider.class)).delta.y;

        int left2 = entity2.transform.position.x - ((RectCollider) entity2.getComponent(Collider.class)).delta.x;
        int top2 = entity2.transform.position.y - ((RectCollider) entity2.getComponent(Collider.class)).delta.y;
        int right2 = entity2.transform.position.x + ((RectCollider) entity2.getComponent(Collider.class)).delta.x;
        int bottom2 = entity2.transform.position.y + ((RectCollider) entity2.getComponent(Collider.class)).delta.y;

        float x1Speed = 0;
        float y1Speed = 0;

        float x2Speed = 0;
        float y2Speed = 0;

        if (entity1.hashComponent(PhysicsComponent.class))
        {
            x1Speed = ((PhysicsComponent) entity1.getComponent(PhysicsComponent.class)).velocity.x;
            y1Speed = ((PhysicsComponent) entity1.getComponent(PhysicsComponent.class)).velocity.y;
        }

        if (entity2.hashComponent(PhysicsComponent.class))
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

        return false; //Rect.intersects(new Rect(left1, top1, right1, bottom1), new Rect(left2, top2, right2, bottom2));
    }*/

    //TODO: Push can be derived from masses for entities.
    private boolean isCollidingV2(Entity entity1, Entity entity2, float push)
    {
        Transform.Position position1 = entity1.transform.position;
        Transform.Position position2 = entity2.transform.position;

        Delta delta1 = ((Collider) entity1.getComponent(Collider.class)).delta;
        Delta delta2 = ((Collider) entity2.getComponent(Collider.class)).delta;

        float deltaXBetweenEntities = position1.x - position2.x;
        float deltaYBetweenEntities = position1.y - position2.y;

        float intersextX = Math.abs(deltaXBetweenEntities) - (delta1.x + delta2.x);
        float intersextY = Math.abs(deltaYBetweenEntities) - (delta1.y + delta2.y);

        if (intersextX < 0 && intersextY < 0)
        {
            push = Math.min(Math.max(push, 0.0f), 1.0f);
            if (intersextX > intersextY)
            {
                if (deltaXBetweenEntities > 0.0f)
                {
                    move(position1, intersextX * (1 - push), 0.0f);
                    move(position2, -intersextX * push, 0.0f);
                } else
                {
                    move(position1, -intersextX * push, 0.0f);
                    move(position2, intersextX * (1 - push), 0.0f);
                }
            } else
            {
                if (deltaYBetweenEntities > 0.0f)
                {
                    move(position1, 0.0f, intersextY * (1 - push));
                    move(position2, 0.0f, -intersextY * push);
                } else
                {
                    move(position1, 0.0f, -intersextY * push);
                    move(position2, 0.0f, intersextY * (1 - push));
                }
            }
            return true;
        }
        return false;
    }

    private void move(Transform.Position position, float x, float y)
    {
        position.x += x;
        position.y += y;
    }
}
