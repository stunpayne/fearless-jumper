package com.stunapps.fearlessjumper.game.loop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.game.Time;

import static com.stunapps.fearlessjumper.game.Time.ONE_MILLION;

/**
 * Created by sunny.s on 10/01/18.
 */

public class MainThread extends Thread
{
	private static final String TAG = MainThread.class.getSimpleName();
	public int maxFps = 60;
	private final SurfaceHolder surfaceHolder;
	private BaseView baseView;

	public static Canvas canvas;
	private double averageFPS;
	private Paint fpsPaint;

	/**
	 * State management variables
	 */
	private boolean running;
	private boolean paused = false;
	private final Object pauseLock = new Object();

	@Inject
	public MainThread(SurfaceHolder surfaceHolder, BaseView baseView, int maxFps)
	{
		this.surfaceHolder = surfaceHolder;
		this.baseView = baseView;
		this.maxFps = maxFps;

		fpsPaint = new Paint();
		fpsPaint.setColor(Color.MAGENTA);
		fpsPaint.setTextSize(40);
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
		long timeMillis = 1000 / maxFps;
		long waitTime;
		int frameCount = 0;
		long totalTime = 0;
		long targetTime = 1000 / maxFps;

		long pauseStartTime = startTime;
		long pauseEndTime = startTime;

		while (running)
		{
			synchronized (pauseLock)
			{
				if (!running)
				{ // may have changed while waiting to
					// synchronize on pauseLock
					break;
				}
				if (paused)
				{
					try
					{
						pauseStartTime = System.nanoTime();
						pauseLock.wait(); // will cause this Thread to block until
						// another thread calls pauseLock.notifyAll()
						// Note that calling wait() will
						// relinquish the synchronized lock that this
						// thread holds on pauseLock so another thread
						// can acquire the lock to call notifyAll()
						// (link with explanation below this code)
					}
					catch (InterruptedException ex)
					{
						break;
					}
					if (!running)
					{ // running might have changed since we paused
						break;
					}
					pauseEndTime = System.nanoTime();
				}
				else
				{
					pauseStartTime = pauseEndTime = 0;
				}
			}

			lastStartTime = startTime;
			lastStartTime += (pauseEndTime - pauseStartTime);
			startTime = System.nanoTime();
			canvas = null;
			try
			{
				canvas = this.surfaceHolder.lockCanvas();
				Environment.CANVAS = canvas;
				synchronized (surfaceHolder)
				{
					Time.DELTA_TIME =
							(float) ((float) (startTime - lastStartTime) / Time.ONE_SECOND_NANOS);
					Log.v(TAG, "Start: " + startTime + " Last start: " + lastStartTime + " Long:" +
							" " + (startTime - lastStartTime) + "" + " Delta Time: " +
							(float) ((float) (startTime - lastStartTime) / Time.ONE_SECOND_NANOS));
					this.baseView.update(startTime - lastStartTime);
					printFPS(canvas);
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

			if (frameCount == maxFps)
			{
				averageFPS = 1000 / ((totalTime / frameCount) / ONE_MILLION);
				frameCount = 0;
				totalTime = 0;
				Log.w("FPS", "Average: " + averageFPS);
			}
		}
	}

	public void pauseThread() throws InterruptedException
	{
		// you may want to throw an IllegalStateException if !running
		paused = true;
	}

	public void resumeThread()
	{
		synchronized (pauseLock)
		{
			paused = false;
			pauseLock.notifyAll(); // Unblocks thread
		}
	}

	public void stopThread()
	{
		running = false;
		resumeThread();
	}

	private void printFPS(Canvas canvas)
	{
		if (Settings.PRINT_FPS)
		{
			if (canvas != null)
			{
				canvas.drawText(String.valueOf(averageFPS), 11 * canvas.getWidth() / 12,
						59 * canvas.getHeight() / 60, fpsPaint);
			}
		}
	}
}
