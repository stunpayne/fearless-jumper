package com.stunapps.fearlessjumper.game.loop;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.manager.ObstacleManager;
import com.stunapps.fearlessjumper.system.Systems;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;
    private GameInitializer gameInitializer;
    private final ObstacleManager obstacleManager;

//    private SceneManager sceneManager;

    @Inject
    public GameView(Context context, GameInitializer gameInitializer, ObstacleManager obstacleManager)
    {
        super(context);
        this.gameInitializer = gameInitializer;
        this.obstacleManager = obstacleManager;
        Constants.CURRENT_CONTEXT = context;
        Log.d("CONTEXT", this.getClass() + " Context hash code: " + context.hashCode());

        getHolder().addCallback(this);
//        thread = new MainThread(getHolder(), this);

//        sceneManager = new SceneManager();
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();

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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //  Call Input System
//        sceneManager.receiveTouch(event);
        return true;
//        return super.onTouchEvent(event);
    }

    public void update(long deltaTime)
    {
//        sceneManager.update();

        if (!gameInitializer.isInitialized())
        {
            gameInitializer.initialize();
        }

        obstacleManager.manage();
        Systems.process(deltaTime);

        invalidate();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
//        sceneManager.draw(canvas);
    }
}
