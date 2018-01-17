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
    private static final boolean debugEnabled = false;

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

        Set<Entity> mobileEntitiesWithPhysics = new HashSet<>();
        Set<Entity> immobileEntities = new HashSet<>();

        for (Entity entity : entities)
        {
            if (entity.hasComponent(PhysicsComponent.class))
            {
                mobileEntitiesWithPhysics.add(entity);
            } else
            {
                immobileEntities.add(entity);
            }
        }

        //TODO: Collision logic.
        for (Entity mobileEntityWithPhysics : mobileEntitiesWithPhysics)
        {
            for (Entity immobileEntity : immobileEntities)
            {
                if (isCollidingV2(mobileEntityWithPhysics, immobileEntity, -0.0f))
                {
                    //TODO: Handle collision.

                }
            }

            for (Entity mobileEntityWithPhysics1 : mobileEntitiesWithPhysics)
            {
                //TODO: Bug, Two different entities will be processed for collision twice here.
                if (!mobileEntityWithPhysics.equals(mobileEntityWithPhysics1))
                {
                    if (isCollidingV2(mobileEntityWithPhysics, mobileEntityWithPhysics1, 0.0f))
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

        //TODO: use physics properties.
        PhysicsComponent physicsComponent1 = (PhysicsComponent)entity1.getComponent(PhysicsComponent.class);
        PhysicsComponent physicsComponent2 = (PhysicsComponent)entity2.getComponent(PhysicsComponent.class);

        Transform.Position position1 = entity1.transform.position;
        Transform.Position position2 = entity2.transform.position;

        Collider collider1 = (Collider)entity1.getComponent(Collider.class);
        Collider collider2 = (Collider)entity2.getComponent(Collider.class);

        float deltaXBetweenEntities = collider1.getCenter(position1).x - collider2.getCenter(position2).x;
        float deltaYBetweenEntities = collider1.getCenter(position1).y - collider2.getCenter(position2).y;

        if(debugEnabled)
        {
            Log.d("CollisionSystem", "Entity1-> x: " + position1.x + ", y: " + position1.y + ", width1: " + collider1.width + ", height1: " + collider1.height);
            Log.d("CollisionSystem", "Entity2-> x: " + position2.x + ", y: " + position2.y + ", width2: " + collider2.width + ", height2: " + collider2.height);
        }

        float intersectX = Math.abs(deltaXBetweenEntities) - (collider1.width/2 + collider2.width/2);
        float intersectY = Math.abs(deltaYBetweenEntities) - (collider1.height/2 + collider2.height/2);

        if (intersectX < 0 && intersectY < 0)
        {
            push = Math.min(Math.max(push, 0.0f), 1.0f);
            if (intersectX > intersectY)
            {
                if (deltaXBetweenEntities > 0.0f)
                {
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaXBetweenEntities < 0 :position1: ");
                    }
                    move(position1, -intersectX * (1 - push), 0.0f);
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaXBetweenEntities < 0 :position2: ");
                    }
                    move(position2, intersectX * push, 0.0f);
                } else
                {
                    Log.d("CollisionSystem", "deltaXBetweenEntities > 0 :position1: ");
                    move(position1, intersectX * (1 - push), 0.0f);
                    Log.d("CollisionSystem", "deltaXBetweenEntities > 0 :position2: ");
                    move(position2, -intersectX * push, 0.0f);
                }
            } else
            {
                if (deltaYBetweenEntities > 0.0f)
                {
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaYBetweenEntities > 0 :position1: ");
                    }
                    move(position1, 0.0f, -intersectY * (1 - push));
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaYBetweenEntities > 0 :position2: ");
                    }
                    move(position2, 0.0f, intersectY * push);
                } else
                {
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaYBetweenEntities < 0 :position1: ");
                    }
                    move(position1, 0.0f, intersectY * (1 - push));
                    if(debugEnabled)
                    {
                        Log.d("CollisionSystem", "deltaYBetweenEntities < 0 :position2: ");
                    }
                    move(position2, 0.0f, -intersectY * push);
                }
            }
            return true;
        }
        return false;
    }

    private void move(Transform.Position position, float x, float y)
    {
        if(debugEnabled)
        {
            Log.d("CollisionSystem", "Delta-> x: " + x + ", y: " + y);
            Log.d("CollisionSystem", "Before-> x: " + position.x + ", y: " + position.y);
        }
        position.x += x;
        position.y += y;
        if(debugEnabled)
        {
            Log.d("CollisionSystem", "After-> x: " + position.x + ", y: " + position.y);
        }
    }
}
