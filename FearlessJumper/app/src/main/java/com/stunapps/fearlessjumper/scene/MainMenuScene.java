package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.event.model.game.ParticleTestEvent;
import com.stunapps.fearlessjumper.event.model.game.StartGameEvent;
import com.stunapps.fearlessjumper.helper.UIBridge;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	private static final String TAG = MainMenuScene.class.getSimpleName();

	@LayoutRes
	private final int mainMenuLayout;
	@LayoutRes
	private final int optionsMenuLayout;
	private View currentActiveView;

	private final UIBridge uiBridge;
	private final Context mContext;
	private final MenuSetup menuSetup = new MenuSetup();

	@Inject
	public MainMenuScene(EventSystem eventSystem, UIBridge uiBridge, final Context mContext,
			MainActivity mainActivity)
	{
		super(R.layout.menu_container, eventSystem, mainActivity);
		this.uiBridge = uiBridge;
		this.mContext = mContext;
		this.mainMenuLayout = R.layout.main_menu;
		this.optionsMenuLayout = R.layout.options_menu;
	}

	@Override
	void setUpScene()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				switchToMenu(mainMenuLayout);
				return null;
			}
		});
	}

	@Override
	public void playScene()
	{

	}

	@Override
	void pauseScene()
	{

	}

	@Override
	void stopScene()
	{

	}

	@Override
	void resumeScene()
	{

	}

	@Override
	public void terminateScene()
	{

	}

	private void showOptionsMenu()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				switchToMenu(optionsMenuLayout);
				return null;
			}
		});
	}

	private void goBackToMainMenu()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				switchToMenu(mainMenuLayout);
				return null;
			}
		});
	}

	private void switchToMenu(@LayoutRes int newMenuId)
	{
		if (null != currentActiveView)
		{
			((FrameLayout) view).removeView(currentActiveView);
		}

		currentActiveView = menuSetup.setupMenu(newMenuId);
		((FrameLayout) this.view).addView(currentActiveView);
	}


	private class MenuSetup
	{
		public View setupMenu(@LayoutRes int menuLayoutId)
		{
			View menu = LayoutInflater.from(mContext).inflate(menuLayoutId, null);

			if (menuLayoutId == optionsMenuLayout)
			{
				/*
					Options menu buttons
		 		*/
				Button optionsBackButton = menu.findViewById(R.id.optionsBackButton);
				optionsBackButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Options back button pressed");
						goBackToMainMenu();
					}
				});
			}
			else if (menuLayoutId == mainMenuLayout)
			{
				/*
					Main menu buttons
		 		*/
				//	Add listener for startButton
				ImageButton startButton = menu.findViewById(R.id.startButton);
				startButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Start button pressed");
						eventSystem.raiseEvent(new StartGameEvent());
					}
				});

				//	Add listener for particleTestButton
				Button optionsMenuButton = menu.findViewById(R.id.optionsMenuButton);
				optionsMenuButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Options menu button pressed");
						showOptionsMenu();
					}
				});

				//	Add listener for particleTestButton
				Button particleTestButton = menu.findViewById(R.id.particleTestButton);
				particleTestButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Particle test button pressed");
						eventSystem.raiseEvent(new ParticleTestEvent());
					}
				});

				//	Add listener for quit button
				ImageButton quitButton = menu.findViewById(R.id.quitButton);
				quitButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Quit button pressed");
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(1);
					}
				});
			}

			return menu;
		}
	}

}
