package com.stunapps.fearlessjumper.display;

import android.support.annotation.Nullable;

import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.entity.Entity;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunny.s on 25/01/18.
 */

public class Cameras
{
    @Getter
    @Setter
    public static Camera mainCamera = null;

    public static List<Camera> activeCameras = Lists.newArrayList();

    public static Camera create(Camera.CameraMode cameraMode, Position position, @Nullable Entity
            target, boolean lockXTranslate, boolean lockYTranslate)
    {
        Camera newCamera = null;
        switch (cameraMode)
        {
            case FOLLOW_TARGET:
                newCamera = new FollowCamera(position, target, lockXTranslate, lockYTranslate);
                break;
            case FIXED:
                newCamera = new FixedCamera(position, lockXTranslate, lockYTranslate);
                break;
            default:
                newCamera = new FixedCamera(position, lockXTranslate, lockYTranslate);
                break;
        }
        activeCameras.add(newCamera);
        return newCamera;
    }

    public static void update()
    {
        for (Camera camera : activeCameras)
        {
            camera.update();
        }
    }
}
