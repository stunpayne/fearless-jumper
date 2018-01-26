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

    public static Camera createFixedCamera(Position position, boolean lockXTranslate,
            boolean lockYTranslate)
    {
        Camera newCamera = new FixedCamera(position, lockXTranslate, lockYTranslate);
        activeCameras.add(newCamera);
        return newCamera;
    }

    public static Camera createFollowCamera(Position position,
            @Nullable Entity target, boolean lockXTranslate, boolean lockYTranslate,
            float maxDeltaX, float maxDeltaY)
    {
        Camera newCamera = new FollowCamera(position, target, lockXTranslate, lockYTranslate,
                maxDeltaX, maxDeltaY);
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