package com.stunapps.fearlessjumper.display;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.model.Position;

import lombok.Getter;

/**
 * Created by sunny.s on 25/01/18.
 */

public class FollowCamera extends Camera
{
    @Getter
    private final Entity target;

    //  Max horizontal distance allowed to target from camera
    private float minDeltaX;
    //  Max vertical distance allowed to target from camera
    private float minDeltaY;

    public FollowCamera(Position position, Entity target, boolean xTranslateLocked,
            boolean yTranslateLocked, float minDeltaX, float minDeltaY)
    {
        super(CameraMode.FOLLOW_TARGET, position, xTranslateLocked, yTranslateLocked);
        this.minDeltaX = minDeltaX;
        this.minDeltaY = minDeltaY;
        this.target = target;
    }

    public void update()
    {
        //  If x movement is allowed, calculate delta in x and move
        float cutoffX = position.x + minDeltaX;
        if (!xTranslateLocked && target.transform.position.x <= cutoffX)
        {
            position.x = target.transform.position.x - minDeltaX;
            //  Apply y limits
            position.x = Math.min(Math.max(position.x, 0), Device.SCREEN_WIDTH);
        }
        
        //  If y movement is allowed, calculate delta in y and move
        float cutoffY = position.y + minDeltaY;
        if (!yTranslateLocked && target.transform.position.y <= cutoffY)
        {
            position.y = target.transform.position.y - minDeltaY;
            //  Apply y limits
            position.y = Math.min(position.y, 0);
        }
    }

    @Override
    protected FollowCamera clone() throws CloneNotSupportedException
    {
        super.clone();
        return new FollowCamera(position, target, xTranslateLocked, yTranslateLocked, minDeltaX,
                minDeltaY);
    }
}
