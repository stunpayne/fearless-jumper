package com.stunapps.fearlessjumper.scene;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.StartGameEvent;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	@Inject
	public MainMenuScene(EventSystem eventSystem, SoundSystem soundSystem)
	{
		super(R.layout.main_menu, eventSystem, soundSystem);
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
				eventSystem.raiseEvent(new StartGameEvent());
			}
		});


		//	Add listener for quit button
		ImageButton quitButton = view.findViewById(R.id.quitButton);
		quitButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
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
	public void terminateScene()
	{

	}

}
