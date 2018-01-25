package com.stunapps.fearlessjumper.display;

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

    //  Position should actually be named Vector. Need to think this through.
    //  For now, just leaving it as Position.
    private Position deltaFromTarget;

    public FollowCamera(Position position, Entity target, boolean xTranslateLocked,
                        boolean yTranslateLocked)
    {
        super(CameraMode.FOLLOW_TARGET, position, xTranslateLocked, yTranslateLocked);
        this.target = target;
        deltaFromTarget = target.transform.position.distanceFrom(this.position);
    }

    public void update()
    {
        //  If x movement is allowed, calculate delta in x and move
        if (!xTranslateLocked)
        {

        }
        //  Apply x limits
        //  If y movement is allowed, calculate delta in y and move
        if (!yTranslateLocked)
        {
            position.y = target.transform.position.y - deltaFromTarget.y;
            //  Apply y limits
            position.y = Math.min(position.y, Constants.SCREEN_HEIGHT / 2);
        }
    }

    @Override
    protected FollowCamera clone() throws CloneNotSupportedException
    {
        super.clone();
        return new FollowCamera(position, target, xTranslateLocked, yTranslateLocked);
    }
}
