package com.stunapps.fearlessjumper.scene;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.helper.Environment;

import lombok.Getter;
import lombok.Setter;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 12/02/18.
 */

public abstract class AbstractScene implements Scene
{
	@Getter
	@Setter
	View view;

	protected final EventSystem eventSystem;
	protected final SoundSystem soundSystem;

	public AbstractScene(View view, EventSystem eventSystem, SoundSystem soundSystem)
	{
		this.view = view;
		this.eventSystem = eventSystem;
		this.soundSystem = soundSystem;
		setUpScene();
	}

	public AbstractScene(@LayoutRes int layoutResId, EventSystem eventSystem,
			SoundSystem soundSystem)
	{
		this.view = LayoutInflater.from(Environment.CONTEXT).inflate(layoutResId, null);
		this.eventSystem = eventSystem;
		this.soundSystem = soundSystem;
		setUpScene();
	}

	@Override
	public void terminate()
	{
		tearDownScene();
		soundSystem.stopSceneMusic(0);
		eventSystem.raiseEvent(Event.SCENE_STOP, new BaseEventInfo());
	}

	@Override
	public final void setActive()
	{
		try
		{
			eventSystem.raiseEvent(Event.SCENE_START, new BaseEventInfo());
			requestViewLoad(view);
		}
		catch (Exception e)
		{
			Log.e("VIEW_LOAD", "View could not be loaded!");
		}
	}

	@Override
	public void play()
	{
		playScene();
	}

	abstract void setUpScene();

	abstract void tearDownScene();

	abstract void playScene();
}
