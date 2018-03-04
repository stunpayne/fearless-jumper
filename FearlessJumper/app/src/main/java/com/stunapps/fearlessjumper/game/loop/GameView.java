package com.stunapps.fearlessjumper.game.loop;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.system.Systems;

import lombok.Getter;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private final static String TAG = GameView.class.getSimpleName();
    private MainThread thread;
    private GameInitializer gameInitializer;

    @Getter
    private boolean paused = true;

    @Inject
    public GameView(Context context, GameInitializer gameInitializer)
    {
        super(context);
        this.gameInitializer = gameInitializer;

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        Log.i(TAG, "Surface created");
        Environment.INIT_TIME = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
//        boolean retry = true;
//        while (retry)
//        {
//            try
//            {
//                thread.setRunning(false);
//                thread.join();
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            retry = false;
//        }

    }

    public void start()
    {
        try
        {
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
            thread.pauseThread();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void pause()
    {
        try
        {
            thread.pauseThread();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        paused = true;
    }

    public void resume()
    {
        paused = false;
        thread.resumeThread();
    }

    public void stop()
    {
		thread.stopThread();
    }

    public void terminate()
    {
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        paused = true;
        gameInitializer.destroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //  Call Input System
        Systems.processInput(event);
        return true;
    }



    public void update(long deltaTime)
    {
        if (!gameInitializer.isInitialized())
        {
            Log.d(TAG, "update: start time = "+System.currentTimeMillis());
            gameInitializer.initialize();
            Log.d(TAG, "update: end time = "+System.currentTimeMillis());
            int i = 0;
        }

        Systems.process(deltaTime);

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }
}
