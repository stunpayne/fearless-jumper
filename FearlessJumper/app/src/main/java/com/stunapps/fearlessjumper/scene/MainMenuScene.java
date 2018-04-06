package com.stunapps.fearlessjumper.scene;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.event.model.game.ParticleTestEvent;
import com.stunapps.fearlessjumper.event.model.game.StartGameEvent;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	private static final String TAG = MainMenuScene.class.getSimpleName();

	@Inject
	public MainMenuScene(EventSystem eventSystem)
	{
		super(R.layout.main_menu, eventSystem);
	}

	@Override
	void setUpScene()
	{
		//	Add listener for startButton
		ImageButton startButton = view.findViewById(R.id.startButton);
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
		Button particleTestButton = view.findViewById(R.id.particleTestButton);
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
		ImageButton quitButton = view.findViewById(R.id.quitButton);
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

}
