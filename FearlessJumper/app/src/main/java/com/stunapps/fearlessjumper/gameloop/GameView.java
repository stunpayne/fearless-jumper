package com.stunapps.fearlessjumper.gameloop;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;

//    private SceneManager sceneManager;

    public GameView(Context context)
    {
        super(context);
        Constants.CURRENT_CONTEXT = context;

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
//        sceneManager.receiveTouch(event);
        return true;
//        return super.onTouchEvent(event);
    }

    public void update()
    {
//        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
//        sceneManager.draw(canvas);
    }
}
