package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.transform.Position;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;

import android.util.Log;

import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.system.CollisionEvent;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.manager.CollisionLayerManager;
import com.stunapps.fearlessjumper.system.model.CollisionResponse;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Created by sunny.s on 03/01/18.
 */

@Singleton
public class CollisionSystem implements UpdateSystem
{
    public static final float TENTATIVE_X_POSITION_FACTOR = 0.1f;
    public static final float TENTATIVE_Y_POSITION_FACTOR = 0.5f;

    private final ComponentManager componentManager;
    private final EventSystem eventSystem;
    private final CollisionLayerManager collisionLayerManager;

    private static long lastProcessTime = System.nanoTime();

    /**
     * This is not needed as we can use different logging levels like
     * Log.v (verbose), Log.d (debug), Log.i (info) etc
     */
    private static final boolean debugEnabled = false;

    @Inject
    public CollisionSystem(ComponentManager componentManager, EventSystem eventSystem,
            CollisionLayerManager collisionLayerManager)
    {
        this.componentManager = componentManager;
        this.eventSystem = eventSystem;
        this.collisionLayerManager = collisionLayerManager;
    }

    @Override
    public void process(long deltaTime)
    {
        lastProcessTime = System.currentTimeMillis();

        Set<Entity> entities = getCollisionEntities();

        Set<Entity> mobileEntities = new HashSet<>();
        Set<Entity> immobileEntities = new HashSet<>();

        for (Entity entity : entities)
        {
            boolean hasPhysics = entity.hasComponent(PhysicsComponent.class);
            if (hasPhysics)
            {
                PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
                if (physicsComponent.getMass() == Float.MAX_VALUE)
                {
                    immobileEntities.add(entity);
                }
                else
                {
                    mobileEntities.add(entity);
                }
            }
            else
            {
                immobileEntities.add(entity);
            }
        }

        for (Entity mobileEntity : mobileEntities)
        {
            for (Entity immobileEntity : immobileEntities)
            {
                if (isCollisionLayerMaskSet(mobileEntity, immobileEntity) &&
                        isColliding(mobileEntity, immobileEntity))
                {
                    CollisionResponse collisionResponse =
                            resolveCollision(mobileEntity, immobileEntity, -0.0f);
                    eventSystem.raiseEvent(new CollisionEvent(mobileEntity, immobileEntity,
                                                              collisionResponse.collisionFace,
                                                              deltaTime));
                }
            }
        }


        /**
         * Collision among mobile entities.
         */
        List<Entity> mobileEntityList = new LinkedList<>(mobileEntities);
        for (int i = 1; i < mobileEntityList.size(); i++)
        {
            for (int j = i + 1; j < mobileEntityList.size(); j++)
            {
                Entity mobileEntity1 = mobileEntityList.get(i);
                Entity mobileEntity2 = mobileEntityList.get(j);
                if (isCollisionLayerMaskSet(mobileEntity1, mobileEntity2) && isColliding
                    (mobileEntity1, mobileEntity2))
                {
                    float mass1 = 0.0f;
                    float mass2 = 0.0f;

                    if (mobileEntity1.hasComponent(PhysicsComponent.class))
                    {
                        mass1 = mobileEntity1.getComponent(PhysicsComponent.class).mass;
                    }

                    if (mobileEntity2.hasComponent(PhysicsComponent.class))
                    {
                        mass2 = mobileEntity2.getComponent(PhysicsComponent.class).mass;
                    }

                    CollisionResponse collisionResponse = null;
                    if (mass2 > 0)
                    {
                        collisionResponse =
                                resolveCollision(mobileEntity1, mobileEntity2, mass1 / mass2);
                    }
                    else
                    {
                        collisionResponse =
                                resolveCollision(mobileEntity2, mobileEntity1, mass2 / mass1);
                    }
                    eventSystem.raiseEvent(new CollisionEvent(mobileEntity1, mobileEntity2,
                                                              collisionResponse.collisionFace,
                                                              deltaTime));
                }
            }
        }
    }

    //TODO: This has to be modified once layers config is in place.
    private Set<Entity> getCollisionEntities()
    {
        return componentManager.getEntities(Collider.class);
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }

    @Override
    public void reset()
    {
        lastProcessTime = 0;
    }

    private boolean isCollisionLayerMaskSet(Entity entity, Entity entityCollidingWith)
    {
        Collider collider = entity.getComponent(Collider.class);
        Collider collidesWith = entityCollidingWith.getComponent(Collider.class);
        return collisionLayerManager.isCollisionMaskSet(collider, collidesWith);
    }

    //TODO: Push can be derived from masses for entities.
    private static boolean isColliding(Entity physicsEntity, Entity fixedEntity)
    {
        float intersectX = calculateXIntersection(physicsEntity, fixedEntity);
        float intersectY = calculateYIntersection(physicsEntity, fixedEntity);

        if (intersectX < 0 && intersectY < 0)
        {
            return true;
        }
        return false;
    }

    private static CollisionResponse resolveCollision(Entity mobileEntity, Entity immobileEntity, float push)
    {
        //  The two objects are colliding. Now we have to find out how much to move
        //  each object and in which direction, to resolve collision.

        PhysicsComponent physicsComponent1 = mobileEntity.getComponent(
                PhysicsComponent.class);

        float intersectX = calculateXIntersection(mobileEntity, immobileEntity);
        float intersectY = calculateYIntersection(mobileEntity, immobileEntity);

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
            //CollisionResolver.resolveXCollision(mobileEntity, immobileEntity, deltaXBetweenEntities,
            // intersectX, push);
            if (!mobileEntity.getComponent(Collider.class).isTrigger() &&
                    !immobileEntity.getComponent(Collider.class).isTrigger())
            {
                //bridgeGapX(mobileEntity, immobileEntity);

                CollisionResolver.resolveXCollision(mobileEntity, immobileEntity, intersectX, push);
                /**
                 * This can be instead handle with coefficient of restitution.
                 */
                physicsComponent1.getVelocity().x = 0;
            }
            return new CollisionResponse(CollisionResponse.CollisionFace.VERTICAL);
        } else
        {
            //  Collision will be resolved in y axis
            //                resolveYCollision(entity1, entity2, deltaYBetweenEntities,
            // intersectY, push);
            if (!mobileEntity.getComponent(Collider.class).isTrigger() &&
                    !immobileEntity.getComponent(Collider.class).isTrigger())
            {
                //bridgeGapY(mobileEntity, immobileEntity);
                CollisionResolver.resolveYCollision(mobileEntity, immobileEntity, intersectY, push);
                physicsComponent1.getVelocity().y = 0;
            }
            return new CollisionResponse(CollisionResponse.CollisionFace.HORIZONTAL);
        }
    }

    private static float calculateXIntersection(Entity entity1, Entity entity2)
    {
        Position position1 = getTentativePosition(entity1);
        Position position2 = getTentativePosition(entity2);

        Collider collider1 = entity1.getComponent(Collider.class);
        Collider collider2 = entity2.getComponent(Collider.class);

        float deltaXBetweenEntities = collider1.getCenter(position1).x - collider2.getCenter(position2
        ).x;

        return Math.abs(deltaXBetweenEntities) - (collider1.width / 2 + collider2.width / 2);
    }

    private static float calculateYIntersection(Entity entity1, Entity entity2)
    {
        Position position1 = getTentativePosition(entity1);
        Position position2 = getTentativePosition(entity2);

        Collider collider1 = entity1.getComponent(Collider.class);
        Collider collider2 = entity2.getComponent(Collider.class);

        float deltaYBetweenEntities = collider1.getCenter(position1).y - collider2.getCenter(position2
        ).y;

        return Math.abs(deltaYBetweenEntities) - (collider1.height / 2 + collider2.height / 2);
    }


    public static class BridgeGap
    {
        public static void bridgeGapX(Entity physicalEntity, Entity fixedEntity)
        {
            Collider physicalCollider = physicalEntity.getComponent(Collider.class);
            Collider fixedCollider = fixedEntity.getComponent(Collider.class);

            Position physicalEntityPosition = physicalEntity.transform.position;
            Position fixedEntityPosition = fixedEntity.transform.position;

            float currentDeltaX = physicalCollider.getCenter(physicalEntityPosition
            ).x - fixedCollider.getCenter(fixedEntityPosition).x;
            float currentSeparationX = Math.abs(
                    currentDeltaX) - (physicalCollider.width / 2 + fixedCollider.width / 2);

            PhysicsComponent physicsComponent = physicalEntity.getComponent(
                    PhysicsComponent.class);
            physicalEntity.transform.position.x += sign(
                    physicsComponent.getVelocity().x) * currentSeparationX;

            if (Float.isNaN(physicalEntity.transform.position.x))
                Log.d("BAD_CASE", "Position  is Nan");
        }

        public static void bridgeGapY(Entity physicalEntity, Entity fixedEntity)
        {
            Collider physicalCollider = physicalEntity.getComponent(Collider.class);
            Collider fixedCollider = fixedEntity.getComponent(Collider.class);

            Position physicalPosition = physicalEntity.transform.position;
            Position fixedPosition = fixedEntity.transform.position;

            float currentDeltaY = physicalCollider.getCenter(physicalPosition
            ).y - fixedCollider.getCenter(fixedPosition).y;
            float currentSeparationY = Math.abs(
                    currentDeltaY) - (physicalCollider.height / 2 + fixedCollider.height / 2);

            PhysicsComponent physicsComponent = physicalEntity.getComponent(
                    PhysicsComponent.class);
            physicalEntity.transform.position.y += sign(
                    physicsComponent.getVelocity().y) * currentSeparationY;
        }

        /**
         * Returns the sign (+/-) of a float number
         *
         * @param number the float number
         * @return the sign (+/-) of a float number
         */
        private static float sign(float number)
        {
            return number < 0 ? -1 : 1;
        }
    }


    private static class CollisionResolver
    {
        public static void resolveXCollision(Entity entity1, Entity entity2,
                                             float intersectionMag, float push)
        {
            if ((entity1.transform.position.x - entity2.transform.position.x) > 0.0f)
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

        public static void resolveYCollision(Entity entity1, Entity entity2,
                                             float intersectionMag, float push)
        {
            if ((entity1.transform.position.y - entity2.transform.position.y) > 0.0f)
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

        private static void move(Position position, float x, float y)
        {
            Log.d("CollisionSystem", "Delta-> x: " + x + ", y: " + y);
            Log.d("CollisionSystem", "Before-> x: " + position.x + ", y: " + position.y);

            position.x += x;
            position.y += y;

            Log.d("CollisionSystem", "After-> x: " + position.x + ", y: " + position.y);
        }
    }

    /**
     * Method to get an entity's position in the next frame if it travels at its current velocity
     * Uses current position and current velocity to calculate the next position
     *
     * @return Position, updated at new position
     */
    private static Position getTentativePosition(Entity entity)
    {

        PhysicsComponent physicsComponent = entity.getComponent(
                PhysicsComponent.class);

        if (physicsComponent == null) return entity.transform.position;
        return new Position(entity.transform.position.x +
                                    physicsComponent.getVelocity().x * TENTATIVE_X_POSITION_FACTOR,
                            entity.transform.position.y +
                                    physicsComponent.getVelocity().y * TENTATIVE_Y_POSITION_FACTOR);
    }
}
