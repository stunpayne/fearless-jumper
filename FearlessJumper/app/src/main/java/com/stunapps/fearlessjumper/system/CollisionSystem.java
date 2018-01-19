package com.stunapps.fearlessjumper.system;

import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;

import android.util.Log;

import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by sunny.s on 03/01/18.
 */

public class CollisionSystem implements System
{

    private final ComponentManager componentManager;

    /**
     * This is not needed as we can use different logging levels like
     * Log.v (verbose), Log.d (debug), Log.i (info) etc
     */
    private static final boolean debugEnabled = false;

    @Inject
    public CollisionSystem(ComponentManager componentManager)
    {
        this.componentManager = componentManager;
    }

    @Override
    public void process(long deltaTime)
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

            //  Not needed as of now
//            for (Entity mobileEntityWithPhysics1 : mobileEntitiesWithPhysics)
//            {
//                //TODO: Bug, Two different entities will be processed for collision twice here.
//                if (!mobileEntityWithPhysics.equals(mobileEntityWithPhysics1))
//                {
//                    if (isCollidingV2(mobileEntityWithPhysics, mobileEntityWithPhysics1, 0.0f))
//                    {
//                        //TODO: handle collision.
//                    }
//                }
//            }
        }
    }

    //TODO: Push can be derived from masses for entities.
    private boolean isCollidingV2(Entity entity1, Entity entity2, float push)
    {

        //TODO: use physics properties.
        PhysicsComponent physicsComponent1 = (PhysicsComponent) entity1.getComponent(
                PhysicsComponent.class);
        PhysicsComponent physicsComponent2 = (PhysicsComponent) entity2.getComponent(
                PhysicsComponent.class);

        Position position1 = entity1.transform.position;
        Position position2 = entity2.transform.position;

        Collider collider1 = (Collider) entity1.getComponent(Collider.class);
        Collider collider2 = (Collider) entity2.getComponent(Collider.class);

        float deltaXBetweenEntities = collider1.getCenter(position1).x - collider2.getCenter(
                position2).x;
        float deltaYBetweenEntities = collider1.getCenter(position1).y - collider2.getCenter(
                position2).y;

        Log.v("CollisionSystem", "Entity1-> x: " + position1.x + ", y: " + position1.y + ", " +
                "width1: " + collider1.width + ", height1: " + collider1.height);
        Log.v("CollisionSystem", "Entity2-> x: " + position2.x + ", y: " + position2.y + ", " +
                "width2: " + collider2.width + ", height2: " + collider2.height);

        float intersectX = Math.abs(
                deltaXBetweenEntities) - (collider1.width / 2 + collider2.width / 2);
        float intersectY = Math.abs(
                deltaYBetweenEntities) - (collider1.height / 2 + collider2.height / 2);

        if (intersectX < 0 && intersectY < 0)
        {
            //  The two objects are colliding. Now we have to find out how much to move
            //  each object and in which direction, to resolve collision.

            push = Math.min(Math.max(push, 0.0f), 1.0f);
            if (intersectX > intersectY)
            {
                //  Collision in x axis is of smaller magnitude than that in y axis
                //  So, collision will be resolved in x axis
                resolveXCollision(position1, position2, deltaXBetweenEntities, intersectX, push);
                physicsComponent1.velocity.x = 0;
            } else
            {
                //  Collision will be resolved in y axis
                resolveYCollision(position1, position2, deltaYBetweenEntities, intersectY, push);
                physicsComponent1.velocity.y = 0;
            }
            return true;
        }
        return false;
    }

    private void move(Position position, float x, float y)
    {
        Log.d("CollisionSystem", "Delta-> x: " + x + ", y: " + y);
        Log.d("CollisionSystem", "Before-> x: " + position.x + ", y: " + position.y);

        position.x += x;
        position.y += y;
        Log.d("CollisionSystem", "After-> x: " + position.x + ", y: " + position.y);

    }

    private void resolveXCollision(Position position1, Position position2, float
            deltaXBetweenEntities, float intersectionMag, float push)
    {
        if (deltaXBetweenEntities > 0.0f)
        {
            Log.v("CollisionSystem", "deltaXBetweenEntities < 0 :position1: ");
            Log.v("CollisionSystem", "deltaXBetweenEntities < 0 :position2: ");

            move(position1, -intersectionMag * (1 - push), 0.0f);
            move(position2, intersectionMag * push, 0.0f);
        } else
        {
            Log.v("CollisionSystem", "deltaXBetweenEntities > 0 :position1: ");
            Log.v("CollisionSystem", "deltaXBetweenEntities > 0 :position2: ");

            move(position1, intersectionMag * (1 - push), 0.0f);
            move(position2, -intersectionMag * push, 0.0f);
        }
    }

    private void resolveYCollision(Position position1, Position position2, float
            deltaYBetweenEntities, float intersectionMag, float push)
    {
        if (deltaYBetweenEntities > 0.0f)
        {
            Log.v("CollisionSystem", "deltaYBetweenEntities > 0 :position1: ");
            Log.v("CollisionSystem", "deltaYBetweenEntities > 0 :position2: ");

            move(position1, 0.0f, -intersectionMag * (1 - push));
            move(position2, 0.0f, intersectionMag * push);
        } else
        {
            Log.v("CollisionSystem", "deltaYBetweenEntities < 0 :position1: ");
            Log.v("CollisionSystem", "deltaYBetweenEntities < 0 :position2: ");

            move(position1, 0.0f, intersectionMag * (1 - push));
            move(position2, 0.0f, -intersectionMag * push);
        }
    }
}
