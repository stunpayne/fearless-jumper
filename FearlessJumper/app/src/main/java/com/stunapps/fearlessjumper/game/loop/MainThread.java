package com.stunapps.fearlessjumper.game.loop;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.helper.Environment;

import static com.stunapps.fearlessjumper.helper.Environment.Constants.ONE_MILLION;

/**
 * Created by sunny.s on 10/01/18.
 */

public class MainThread extends Thread
{
    public static final int MAX_FPS = 60;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    @Inject
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        long startTime = System.nanoTime();
        long lastStartTime = startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running)
        {
            lastStartTime = startTime;
            startTime = System.nanoTime();
            canvas = null;
            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                Environment.CANVAS = canvas;
                synchronized (surfaceHolder)
                {
                    //  Replace with physics, collision, animation systemType updates
                    this.gameView.update((startTime - lastStartTime));
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
                Log.d("FPS", "Average: " + averageFPS);
            }
        }
    }
}
