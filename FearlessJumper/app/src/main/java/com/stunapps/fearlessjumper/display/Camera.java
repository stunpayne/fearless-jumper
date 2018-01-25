package com.stunapps.fearlessjumper.display;

import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.entity.Entity;

import lombok.Getter;

/**
 * Created by sunny.s on 21/01/18.
 */

public abstract class Camera implements Cloneable
{
    @Getter
    public Position position;
    @Getter
    public CameraMode cameraMode;

    //  Denotes whether the camera can translate horizontally or not. Cannot if true.
    protected boolean xTranslateLocked;
    //  Denotes whether the camera can translate vertically or not. Cannot if true.
    protected boolean yTranslateLocked;

    public Camera(CameraMode cameraMode, Position position, boolean xTranslateLocked,
                  boolean yTranslateLocked)
    {
        this.cameraMode = cameraMode;
        this.position = position;
        this.xTranslateLocked = xTranslateLocked;
        this.yTranslateLocked = yTranslateLocked;
    }

    public enum CameraMode
    {
        FOLLOW_TARGET,
        FIXED
    }

    public abstract void update();
}
