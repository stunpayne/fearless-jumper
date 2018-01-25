package com.stunapps.fearlessjumper.display;

import com.stunapps.fearlessjumper.component.transform.Position;

/**
 * Created by sunny.s on 25/01/18.
 */

public class FixedCamera extends Camera
{
    public FixedCamera(Position position, boolean lockXTranslate, boolean lockYTranslate)
    {
        super(CameraMode.FIXED, position, lockXTranslate, lockYTranslate);
    }

    @Override
    public void update()
    {

    }

    @Override
    protected FixedCamera clone() throws CloneNotSupportedException
    {
        super.clone();
        return new FixedCamera(position, xTranslateLocked, yTranslateLocked);
    }
}
