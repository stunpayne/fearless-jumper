package com.stunapps.fearlessjumper.game.loop;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.system.Systems;

/**
 * Created by sunny.s on 10/01/18.
 */

public class MainThread extends Thread
{
    public static final int MAX_FPS = 30;
    public static final int ONE_MILLION = 1000000;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    //  TODO: Remove after test
    private Rect rect;
    private Rect rect2;
    private int delta = 2;

    @Inject
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

        //  TODO: Remove after test
        rect = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Constants
                .SCREEN_WIDTH / 2 + 100, Constants.SCREEN_HEIGHT / 2 + 100);
        rect2 = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + 50, Constants
                .SCREEN_WIDTH / 2 + 100, Constants.SCREEN_HEIGHT / 2 + 150);
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running)
        {
            startTime = System.nanoTime();
            canvas = null;
            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                Constants.canvas = canvas;
                synchronized (surfaceHolder)
                {
                    //  Replace with physics, collision, animation systemType updates
                    this.gameView.update();
//                    physicsSystem.process();
//                    collisionSystem.process();
//                    animationSystem.process();


                    //  Replace with rendering systemType process
//                    renderSystem.process();
//                    this.gameView.draw(canvas);
                    Systems.process();
//                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                    paint.setColor(Color.BLUE);
//                    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//                    paint2.setColor(Color.RED);
//                    canvas.drawRect(rect2, paint2);
//                    canvas.drawRect(rect, paint);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / ONE_MILLION;
            waitTime = targetTime - timeMillis;
            try
            {
                if (waitTime > 0)
                {
                    sleep(waitTime);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == MAX_FPS)
            {
                averageFPS = 1000 / ((totalTime / frameCount) / ONE_MILLION);
                frameCount = 0;
                totalTime = 0;
                Log.i("FPS", "Average: " + averageFPS);
            }

            //  TODO: Remove after test
            rect.top -= delta;
            rect.bottom -= delta;
            rect2.top -= delta;
            rect2.bottom -= delta;
//            Log.d("TEST", "Rect top new: " + rect.top);
//            Log.d("TEST", "Rect height: " + (rect.top - rect.bottom));


            if (rect.top <= -50)
                delta = -2;
        }
    }
}
