package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	private final EventSystem eventSystem;

	@Inject
	public MainMenuScene(EventSystem eventSystem)
	{
		super(R.layout.main_menu);
		this.eventSystem = eventSystem;
		setupScene();
	}

	@Override
	public void play()
	{

	}

	@Override
	public void terminate()
	{

	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{

	}

	@Override
	void setupScene()
	{
		//	Add listener for startButton
		ImageButton startButton = view.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				eventSystem.raiseEvent(Event.START_GAME, new BaseEventInfo());
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
}
