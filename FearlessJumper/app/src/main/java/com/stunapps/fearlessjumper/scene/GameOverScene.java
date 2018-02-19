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
    public GameOverScene(View view, EventSystem eventSystem, SoundSystem soundSystem)
    {
        super(view, eventSystem, soundSystem);
    }

    @Override
    public void setup()
    {

    }

    @Override
    public void receiveTouch(MotionEvent motionEvent)
    {

    }

    @Override
    public void terminate()
    {
        super.terminate();
    }

    @Override
    public void play()
    {
        super.play();
    }

    @Override
    void setUpScene()
    {

    }

    @Override
    void tearDownScene()
    {

    }

    @Override
    void playScene()
    {

    }
}
