package com.stunapps.fearlessjumper.scene;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.scene.SceneStartEvent;
import com.stunapps.fearlessjumper.event.scene.SceneStopEvent;
import com.stunapps.fearlessjumper.helper.Environment;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

import lombok.Getter;
import lombok.Setter;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 12/02/18.
 */

public abstract class AbstractScene implements Scene
{
    private static final String TAG = "AbstractScene";

    @Getter
    @Setter
    View view;
    protected Integer sceneId;
    protected final EventSystem eventSystem;

    @Inject
    protected MainActivity mainActivity;

    public AbstractScene(View view, EventSystem eventSystem)
    {
        this.view = view;
        this.eventSystem = eventSystem;
    }

    public AbstractScene(@LayoutRes int layoutResId, EventSystem eventSystem)
    {
        this(LayoutInflater.from(Environment.CONTEXT).inflate(layoutResId, null), eventSystem);
    }

    @Override
    public final void setup()
    {
        try
        {
            //eventSystem.raiseEvent(new SceneStartEvent());
            setUpScene();
            requestViewLoad(view);
//            disableSurfaceViewLogging();
        }
        catch (Exception e)
        {
            Log.e("VIEW_LOAD", "View could not be loaded!");
        }
    }

    @Override
    public void play()
    {
        playScene();
    }

    @Override
    public void pause()
    {
        pauseScene();
    }

    @Override
    public void resume()
    {
        resumeScene();
    }

    @Override
    public void terminate()
    {
        terminateScene();
        eventSystem.raiseEvent(new SceneStopEvent());
    }

    protected void modifyScene(final SceneModificationCallback callable)
    {
        mainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    callable.call();
                }
                catch (Exception e)
                {
                    Log.e("MAIN_ACTIVITY", "Exception occurred while modifying scene");
                }
            }
        });
    }

    abstract void setUpScene();

    abstract void playScene();

    abstract void pauseScene();

    abstract void resumeScene();

    abstract void terminateScene();

    private void disableSurfaceViewLogging() {
        try {
            Field debug = SurfaceView.class.getDeclaredField("DEBUG");
            Field info = SurfaceView.class.getDeclaredField("INFO");
            debug.setAccessible(true);
            debug.set(null, false);
            info.setAccessible(true);
            info.set(null, false);
            Log.i(TAG, "SurfaceView debug disabled");
        } catch (Exception e) {
            Log.e(TAG, "while trying to disable debug in SurfaceView", e);
        }
    }

    protected interface SceneModificationCallback extends Callable
    {
    }
}
