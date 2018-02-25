package com.stunapps.fearlessjumper.game.loop;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.system.Systems;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;
    private GameInitializer gameInitializer;

    @Inject
    public GameView(Context context, GameInitializer gameInitializer)
    {
        super(context);
        this.gameInitializer = gameInitializer;
        Environment.CONTEXT = context;

        getHolder().addCallback(this);
        setFocusable(true);
        
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        thread = new MainThread(getHolder(), this);
        Environment.INIT_TIME = System.currentTimeMillis();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    public MainThread getThread()
    {
        return thread;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //  Call Input System
//        sceneManager.receiveTouch(eventType);
        Systems.processInput(event);
        return true;
//        return super.onTouchEvent(eventType);
    }



    public void update(long deltaTime)
    {
//        sceneManager.update();

        if (!gameInitializer.isInitialized())
        {
            gameInitializer.initialize();
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
