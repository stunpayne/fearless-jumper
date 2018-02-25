package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;
import android.view.View;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.scene.AbstractScene;

/**
 * Created by anand.verma on 17/02/18.
 */

public class GameOverScene extends AbstractScene
{
    @Inject
    public GameOverScene(View view, EventSystem eventSystem)
    {
        super(view, eventSystem);
    }

    @Override
    void setUpScene()
    {

    }

    @Override
    void playScene()
    {

    }

    @Override
    void terminateScene()
    {

    }
}
