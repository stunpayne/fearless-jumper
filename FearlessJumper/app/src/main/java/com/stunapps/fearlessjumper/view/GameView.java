package com.stunapps.fearlessjumper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.game.loop.MainThread;
import com.stunapps.fearlessjumper.init.GameInitializer;
import com.stunapps.fearlessjumper.system.Systems;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameView extends BaseView implements SurfaceHolder.Callback
{
	private final static String TAG = GameView.class.getSimpleName();
	private GameInitializer gameInitializer;

	private MainThread thread;

	/*
		When main thread is stopped, it is not joined immediately as the join, if called
		from inside the thread run, will lead to inconsistent state. Hence, this variable
		is used to track the previous thread and join it during the next start operation.
	 */
	private MainThread previousThread;

	private boolean waitingForExplicitResume = false;

	private boolean surfaceCreated = false;

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
		Time.INIT_TIME = System.currentTimeMillis();
		surfaceCreated = true;
		if (!waitingForExplicitResume)
		{
			thread.resumeThread();
		}
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
		surfaceCreated = false;
	}

	public void prepareForStart()
	{
		waitingForExplicitResume = false;
	}

	@Override
	public void start()
	{
		try
		{
			if (previousThread != null)
			{
				previousThread.join();
			}

			thread = new MainThread(getHolder(), this, 60);
			thread.setRunning(true);
			thread.pauseThread();
			thread.start();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
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
	}

	@Override
	public void resume()
	{
		if (surfaceCreated)
		{
			Log.i(TAG, "Resuming thread");
			thread.resumeThread();
		}
	}

	@Override
	public void stop()
	{
		thread.stopThread();
		previousThread = thread;

		waitingForExplicitResume = true;
	}

	@Override
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
		Systems.reset();
		gameInitializer.destroy();

		waitingForExplicitResume = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		Systems.processInput(event);
		return true;
	}


	@Override
	public void update(long deltaTime)
	{
		if (surfaceCreated)
		{
			if (!gameInitializer.isInitialized())
			{
				Log.d(TAG, "update: start time = " + System.currentTimeMillis());
				gameInitializer.initialize();
				Log.d(TAG, "update: end time = " + System.currentTimeMillis());
			}

			Systems.process(deltaTime);

			postInvalidate();
		}
	}

	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
	}
}
