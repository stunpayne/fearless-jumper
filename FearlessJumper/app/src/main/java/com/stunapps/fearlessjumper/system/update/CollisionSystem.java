package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.component.transform.Position;

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

public class CollisionSystem implements UpdateSystem
{

    private final ComponentManager componentManager;
    private static long lastProcessTime = System.nanoTime();

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
        lastProcessTime = System.currentTimeMillis();
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

        }
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }

    //TODO: Push can be derived from masses for entities.
    private boolean isCollidingV2(Entity physicsEntity, Entity fixedEntity, float push)
    {
        PhysicsComponent physicsComponent1 = (PhysicsComponent) physicsEntity.getComponent(
                PhysicsComponent.class);

        Position position1 = getTentativePosition(physicsEntity);
        Position position2 = getTentativePosition(fixedEntity);

        Collider collider1 = (Collider) physicsEntity.getComponent(Collider.class);
        Collider collider2 = (Collider) fixedEntity.getComponent(Collider.class);

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

            /** Instead of considering current positions for collision, we are now considering
             *  the positions that the entities will be at in the next frame, if they travel at
             *  their current velocities. If the entities collide at their next positions, we
             *  bridge the gap between them right now and set their velocities to zero.
             */
            push = Math.min(Math.max(push, 0.0f), 1.0f);
            if (intersectX > intersectY)
            {
                /**
                 *  Collision in x axis is of smaller magnitude than that in y axis
                 *  So, collision will be resolved in x axis.
                 */
//                resolveXCollision(entity1, entity2, deltaXBetweenEntities, intersectX, push);
                bridgeGapX(physicsEntity, fixedEntity);
                physicsComponent1.velocity.x = 0;
            } else
            {
                //  Collision will be resolved in y axis
//                resolveYCollision(entity1, entity2, deltaYBetweenEntities, intersectY, push);
                bridgeGapY(physicsEntity, fixedEntity);
                physicsComponent1.velocity.y = 0;
            }
            return true;
        }
        return false;
    }

    private void resolveXCollision(Entity entity1, Entity entity2, float
            deltaXBetweenEntities, float intersectionMag, float push)
    {
        if (deltaXBetweenEntities > 0.0f)
        {
            Log.v("CollisionSystem", "deltaXBetweenEntities < 0 :position1: ");
            Log.v("CollisionSystem", "deltaXBetweenEntities < 0 :position2: ");

            move(entity1.transform.position, -intersectionMag * (1 - push), 0.0f);
            move(entity2.transform.position, intersectionMag * push, 0.0f);
        } else
        {
            Log.v("CollisionSystem", "deltaXBetweenEntities > 0 :position1: ");
            Log.v("CollisionSystem", "deltaXBetweenEntities > 0 :position2: ");

            move(entity1.transform.position, intersectionMag * (1 - push), 0.0f);
            move(entity2.transform.position, -intersectionMag * push, 0.0f);
        }
    }

    private void resolveYCollision(Entity entity1, Entity entity2, float
            deltaYBetweenEntities, float intersectionMag, float push)
    {
        if (deltaYBetweenEntities > 0.0f)
        {
            Log.v("CollisionSystem", "deltaYBetweenEntities > 0 :position1: ");
            Log.v("CollisionSystem", "deltaYBetweenEntities > 0 :position2: ");

            move(entity1.transform.position, 0.0f, -intersectionMag * (1 - push));
            move(entity2.transform.position, 0.0f, intersectionMag * push);
        } else
        {
            Log.v("CollisionSystem", "deltaYBetweenEntities < 0 :position1: ");
            Log.v("CollisionSystem", "deltaYBetweenEntities < 0 :position2: ");

            move(entity1.transform.position, 0.0f, intersectionMag * (1 - push));
            move(entity2.transform.position, 0.0f, -intersectionMag * push);
        }
    }

    private void move(Position position, float x, float y)
    {
        Log.d("CollisionSystem", "Delta-> x: " + x + ", y: " + y);
        Log.d("CollisionSystem", "Before-> x: " + position.x + ", y: " + position.y);

        position.x += x;
        position.y += y;

        Log.d("CollisionSystem", "After-> x: " + position.x + ", y: " + position.y);
    }

    private void bridgeGapY(Entity physicalEntity, Entity fixedEntity)
    {
        Collider physicalCollider = (Collider) physicalEntity.getComponent(Collider.class);
        Collider fixedCollider = (Collider) fixedEntity.getComponent(Collider.class);

        Position physicalPosition = physicalEntity.transform.position;
        Position fixedPosition = fixedEntity.transform.position;

        float currentDeltaY = physicalCollider.getCenter(
                physicalPosition).y - fixedCollider.getCenter(
                fixedPosition).y;
        float currentSeparationY = Math.abs(currentDeltaY) -
                (physicalCollider.height / 2 + fixedCollider.height / 2);

        PhysicsComponent physicsComponent = (PhysicsComponent) physicalEntity.getComponent(
                PhysicsComponent.class);
        physicalEntity.transform.position.y += sign(
                physicsComponent.velocity.y) * currentSeparationY;
    }

    private void bridgeGapX(Entity physicalEntity, Entity fixedEntity)
    {
        Collider physicalCollider = (Collider) physicalEntity.getComponent(Collider.class);
        Collider fixedCollider = (Collider) fixedEntity.getComponent(Collider.class);

        Position physicalEntityPosition = physicalEntity.transform.position;
        Position fixedEntityPosition = fixedEntity.transform.position;

        float currentDeltaX = physicalCollider.getCenter(
                physicalEntityPosition).x - fixedCollider.getCenter(fixedEntityPosition).x;
        float currentSeparationX = Math.abs(currentDeltaX) -
                (physicalCollider.width / 2 + fixedCollider.width / 2);

        PhysicsComponent physicsComponent = (PhysicsComponent) physicalEntity.getComponent(
                PhysicsComponent.class);
        physicalEntity.transform.position.x += sign(
                physicsComponent.velocity.x) * currentSeparationX;
    }

    private float sign(float number)
    {
        return number / Math.abs(number);
    }

    /**
     * Method to get an entity's position in the next frame if it travels at its current velocity
     * Uses current position and current velocity to calculate the next position
     *
     * @return Position, updated at new position
     */
    private Position getTentativePosition(Entity entity)
    {
        PhysicsComponent physicsComponent = (PhysicsComponent) entity.getComponent(
                PhysicsComponent.class);

        if (physicsComponent == null)
            return entity.transform.position;
        return new Position(entity.transform.position.x + physicsComponent.velocity.x,
                entity.transform.position.y + physicsComponent.velocity.y);
    }
}
