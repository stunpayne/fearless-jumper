package com.stunapps.fearlessjumper.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand.verma on 30/12/17.
 */

@Singleton
public class SceneManager
{
    private List<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager()
    {
        scenes.add(new MainMenuScene());
        ACTIVE_SCENE = 0;
    }

    public void update()
    {
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas)
    {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public void receiveTouch(MotionEvent motionEvent){
        scenes.get(ACTIVE_SCENE).receiveTouch(motionEvent);
    }
}
