package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

/**
 * This component can be used to provide automated periodic translation
 * to an entity. If an x movement is provided, the entity will move
 * periodically in the x axis between the bounds provided at the mentioned speed.
 * Should only be used on an entity that has a PhysicsComponent
 * <p>
 * Created by sunny.s on 21/01/18.
 */

public class PeriodicTranslation extends Component
{
    //  Max deviation leftward from initial X position
    public float minX = 0.0f;

    //  Max deviation rightward from initial X position
    public float maxX = 0.0f;

    //  Speed at which the entity will move
    public float speedX = 0.0f;

    //  Denotes whether the entity has an x movement
    //  Set to true automatically upon providing an x movement
    private boolean movesInX = false;


    //  Max deviation upward from initial X position
    public float minY = 0.0f;

    //  Max deviation downward from initial X position
    public float maxY = 0.0f;

    //  Speed at which the entity will move
    public float speedY = 0.0f;

    //  Denotes whether the entity has a y movement
    //  Set to true automatically upon providing a y movement
    private boolean movesInY = false;

    //  Speed at which the entity is currently moving horizontally
    private float currSpeedX = 0.0f;

    //  Speed at which the entity is currently moving vertically
    private float currSpeedY = 0.0f;

    public PeriodicTranslation()
    {
        super(PeriodicTranslation.class);
    }

    public PeriodicTranslation(float minX, float maxX, float speedX, float minY, float maxY,
                               float speedY)
    {
        this();
        this.withXMovement(minX, maxX, speedX).withYMovement(minY, maxY, speedY);
    }

    public PeriodicTranslation withXMovement(float minX, float maxX, float speedX)
    {
        this.minX = minX;
        this.maxX = maxX;
        this.speedX = speedX;
        this.currSpeedX = speedX;

        this.movesInX = true;

        return this;
    }

    public PeriodicTranslation withYMovement(float minY, float maxY, float speedY)
    {
        this.minY = minY;
        this.maxY = maxY;
        this.speedY = speedY;
        this.currSpeedY = speedY;

        this.movesInY = true;

        return this;
    }

    public boolean movesInX()
    {
        return movesInX;
    }

    public boolean movesInY()
    {
        return movesInY;
    }

    public float getCurrSpeedX()
    {
        return currSpeedX;
    }

    public float getCurrSpeedY()
    {
        return currSpeedY;
    }

    public void setCurrSpeedX(float newSpeedX)
    {
        this.currSpeedX = newSpeedX;
    }

    public void setCurrSpeedY(float newSpeedY)
    {
        this.currSpeedY = newSpeedY;
    }

    @Override
    public PeriodicTranslation clone() throws CloneNotSupportedException
    {
        PeriodicTranslation clone = new PeriodicTranslation();
        if (movesInX())
            clone = clone.withXMovement(minX, maxX, speedX);
        if (movesInY())
            clone = clone.withYMovement(minY, maxY, speedY);
        return clone;
    }
}
