package com.stunapps.fearlessjumper;

import android.app.Activity;
import android.media.AudioManager;
import android.support.annotation.LayoutRes;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.module.GameModule;
import com.stunapps.fearlessjumper.scene.SceneManager;
import com.stunapps.fearlessjumper.system.Systems;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

import static com.stunapps.fearlessjumper.di.DI.di;

public class MainActivity extends Activity
{
	private static MainActivity instance = null;

	/**
	 * Returns a singleton instance of MainActivity
	 * Must never be called before onCreate is completed
	 *
	 * @return
	 */
	public static MainActivity getInstance()
	{
		//TODO: On instance being null, it should throw exception.
		if (instance == null)
		{
			instance = new MainActivity();
		}
		return instance;
	}

	public LoadViewCallback getLoadViewCallback(View view)
	{
		return new LoadViewCallback(view);
	}

	public LoadViewCallback getLoadViewCallback(@LayoutRes int layoutResId)
	{
		return new LoadViewCallback(layoutResId);
	}

	public class LoadViewCallback implements Callable
	{
		final View viewToLoad;

		@LayoutRes
		int layoutIdToLoad;

		public LoadViewCallback(View viewToLoad)
		{
			this.viewToLoad = viewToLoad;
		}

		public LoadViewCallback(@LayoutRes int layoutIdToLoad)
		{
			this.viewToLoad =
					LayoutInflater.from(Environment.CONTEXT).inflate(layoutIdToLoad, null);
		}

		@Override
		public Boolean call()
		{
			try
			{
				if (viewToLoad != null) loadView(viewToLoad);
				else loadView(layoutIdToLoad);
				return true;
			}
			catch (Exception e)
			{
				Log.e("VIEW_LOAD", "Error occurred while loading view: " + e.getMessage());
			}
			return false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		instance = this;
		super.onCreate(savedInstanceState);

		setupActivityContext();

		Log.d("CONTEXT", "Context hash code: " + this.hashCode());

		DI.install(new GameModule(this));

		Bitmaps.initialise();
		di().getInstance(Systems.class).initialise();
		di().getInstance(SceneManager.class).initialise();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		di().getInstance(SceneManager.class).destroy();
		di().getInstance(Systems.class).reset();
	}

	private void loadView(final View view)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				setContentView(view);
				Log.d("CONTENT_VIEW", "Content view changed");
			}
		});
	}

	private void loadView(@LayoutRes final int layoutResId)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				setContentView(layoutResId);
				Log.d("CONTENT_VIEW", "Content view changed");
			}
		});
	}

	private void setupActivityContext()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Device.SCREEN_WIDTH = dm.widthPixels;
		Device.SCREEN_HEIGHT = dm.heightPixels;
		Device.DISPLAY_DENSITY = getResources().getDisplayMetrics().density;

		Log.d("MAIN_ACTIVITY",
				"Width: " + Device.SCREEN_WIDTH + " Height: " + Device.SCREEN_HEIGHT);
		Environment.CONTEXT = this;
	}

	public void updateScore(final String score)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((TextView) findViewById(R.id.score)).setText(score);
			}
		});
	}

	public void updateTime(final String time)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((TextView) findViewById(R.id.timeout)).setText(time);
			}
		});
	}

	public void updateFuel(final String fuel)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((TextView) findViewById(R.id.fuel)).setText(fuel);
			}
		});
	}

	public void updateHealth(final String health)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((TextView) findViewById(R.id.health)).setText(health);
			}
		});
	}

}
