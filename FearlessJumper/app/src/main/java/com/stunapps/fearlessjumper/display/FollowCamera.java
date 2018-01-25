package com.stunapps.fearlessjumper.display;

import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent.Velocity;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Constants;

import lombok.Getter;

/**
 * Created by sunny.s on 25/01/18.
 */

public class FollowCamera extends Camera
{
    @Getter
    private final Entity target;

    //  Max horizontal distance allowed to target from camera
    private float maxDeltaX;
    //  Max vertical distance allowed to target from camera
    private float maxDeltaY;

    public FollowCamera(Position position, Entity target, boolean xTranslateLocked,
            boolean yTranslateLocked, float maxDeltaX, float maxDeltaY)
    {
        super(CameraMode.FOLLOW_TARGET, position, xTranslateLocked, yTranslateLocked);
        this.maxDeltaX = maxDeltaX;
        this.maxDeltaY = maxDeltaY;
        this.target = target;
    }

    public void update()
    {
        //  If x movement is allowed, calculate delta in x and move
        float cutoffX = position.x + maxDeltaX;
        if (!xTranslateLocked && target.transform.position.x <= cutoffX)
        {
            position.x = target.transform.position.x - maxDeltaX;
            //  Apply y limits
            position.x = Math.min(Math.max(position.x, 0), Constants.SCREEN_WIDTH);
        }
        
        //  If y movement is allowed, calculate delta in y and move
        float cutoffY = position.y + maxDeltaY;
        if (!yTranslateLocked && target.transform.position.y <= cutoffY)
        {
            position.y = target.transform.position.y - maxDeltaY;
            //  Apply y limits
            position.y = Math.min(position.y, 0);
        }
    }

    @Override
    protected FollowCamera clone() throws CloneNotSupportedException
    {
        super.clone();
        return new FollowCamera(position, target, xTranslateLocked, yTranslateLocked, maxDeltaX,
                maxDeltaY);
    }
}
