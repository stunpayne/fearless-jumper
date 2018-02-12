package com.stunapps.fearlessjumper;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.module.GameModule;
import com.stunapps.fearlessjumper.scene.MainMenuScene;
import com.stunapps.fearlessjumper.scene.SceneManager;
import com.stunapps.fearlessjumper.system.Systems;

import java.util.concurrent.Callable;

import static com.stunapps.fearlessjumper.di.DI.di;

public class MainActivity extends Activity
{
	private static MainActivity instance = null;

	/**
	 * Returns a singleton instance of MainActivity
	 * Must never be called before onCreate is completed
	 * @return
	 */
	public static MainActivity getInstance()
	{
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
		View viewToLoad;
		@LayoutRes int layoutIdToLoad;

		public LoadViewCallback(View viewToLoad)
		{
			this.viewToLoad = viewToLoad;
		}

		public LoadViewCallback(@LayoutRes int layoutIdToLoad)
		{
			this.layoutIdToLoad = layoutIdToLoad;
		}

		@Override
		public Boolean call()
		{
			try
			{
				if (viewToLoad != null)
					loadView(viewToLoad);
				else
					loadView(layoutIdToLoad);
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Constants.SCREEN_WIDTH = dm.widthPixels;
		Constants.SCREEN_HEIGHT = dm.heightPixels;
		Constants.DISPLAY_DENSITY = getResources().getDisplayMetrics().density;
		Constants.activity = this;
		Constants.CURRENT_CONTEXT = this;

		Log.d("CONTEXT", "Context hash code: " + this.hashCode());


		DI.install(new GameModule(this));
		di().getInstance(Systems.class).initialise();

//		        setContentView(DI.di().getInstance(GameView.class));
		//        setContentView(R.layout.activity_main);
		di().getInstance(SceneManager.class).start();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		//        di().getInstance(OrientationData.class).register();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		//        di().getInstance(OrientationData.class).pause();
	}

	private void loadView(View view)
	{
		setContentView(view);
		Log.d("CONTENT_VIEW", "Content view changed");
	}

	private void loadView(@LayoutRes int layoutResId)
	{
		setContentView(layoutResId);
		Log.d("CONTENT_VIEW", "Content view changed");
	}
}
