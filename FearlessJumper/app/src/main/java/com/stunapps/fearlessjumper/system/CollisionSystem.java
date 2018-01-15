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
import android.util.Log;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;

import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.HashSet;
import java.util.Set;

import static android.content.ContentValues.TAG;

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
            if (entity.hasComponent(PhysicsComponent.class))
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

    //TODO: Push can be derived from masses for entities.
    private boolean isCollidingV2(Entity entity1, Entity entity2, float push)
    {
        Transform.Position position1 = entity1.transform.position;
        Transform.Position position2 = entity2.transform.position;

        Delta delta1 = ((Collider) entity1.getComponent(Collider.class)).delta;
        Delta delta2 = ((Collider) entity2.getComponent(Collider.class)).delta;

        //Log.d("CollisionSystem", "Entity1-> x: " + position1.x + ", y: " + position1.y + ", width1: " + delta1.x + ", height1: " + delta1.y);
        //Log.d("CollisionSystem", "Entity2-> x: " + position2.x + ", y: " + position2.y + ", width2: " + delta2.x + ", height2: " + delta2.y);
        float deltaXBetweenEntities = position1.x - position2.x;
        float deltaYBetweenEntities = position1.y - position2.y;

        float intersectX = Math.abs(deltaXBetweenEntities) - (delta1.x + delta2.x);
        float intersectY = Math.abs(deltaYBetweenEntities) - (delta1.y + delta2.y);

        if (intersectX < 0 && intersectY < 0)
        {
            push = Math.min(Math.max(push, 0.0f), 1.0f);
            if (intersectX > intersectY)
            {
                if (deltaXBetweenEntities > 0.0f)
                {
                    move(position1, intersectX * (1 - push), 0.0f);
                    move(position2, -intersectX * push, 0.0f);
                } else
                {
                    move(position1, -intersectX * (1 - push), 0.0f);
                    move(position2, intersectX * push, 0.0f);
                }
            } else
            {
                if (deltaYBetweenEntities > 0.0f)
                {
                    //Log.d("CollisionSystem", "deltaYBetweenEntities > 0 :position1: ");
                    move(position1, 0.0f, -intersectY * (1 - push));
                    //Log.d("CollisionSystem", "deltaYBetweenEntities > 0 :position2: ");
                    move(position2, 0.0f, intersectY * push);
                } else
                {
                    //Log.d("CollisionSystem", "deltaYBetweenEntities < 0 :position1: ");
                    move(position1, 0.0f, intersectY * (1 - push));
                    //Log.d("CollisionSystem", "deltaYBetweenEntities < 0 :position2: ");
                    move(position2, 0.0f, -intersectY * push);
                }
            }
            return true;
        }
        return false;
    }

    private void move(Transform.Position position, float x, float y)
    {
        //Log.d("CollisionSystem", "Delta-> x: " + x + ", y: " + y);
        //Log.d("CollisionSystem", "Before-> x: " + position.x + ", y: " + position.y);
        position.x += x;
        position.y += y;
        //Log.d("CollisionSystem", "After-> x: " + position.x + ", y: " + position.y);
    }
}
