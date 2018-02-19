package com.stunapps.fearlessjumper.scene;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.scene.SceneStartEvent;
import com.stunapps.fearlessjumper.event.scene.SceneStopEvent;
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
	protected Integer sceneId;
	protected final EventSystem eventSystem;
	protected final SoundSystem soundSystem;

	public AbstractScene(View view, EventSystem eventSystem, SoundSystem soundSystem)
	{
		this.view = view;
		this.eventSystem = eventSystem;
		this.soundSystem = soundSystem;

	}

	public AbstractScene(@LayoutRes int layoutResId, EventSystem eventSystem,
						 SoundSystem soundSystem)
	{
		this(LayoutInflater.from(Environment.CONTEXT).inflate(layoutResId, null), eventSystem, soundSystem);
	}

	@Override
	public final void setup()
	{
		try
		{
			eventSystem.raiseEvent(new SceneStartEvent());
			requestViewLoad(view);
			setUpScene();
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

	@Override
	public void terminate()
	{
		terminateScene();
		soundSystem.stopSceneMusic(0);
		eventSystem.raiseEvent(new SceneStopEvent());
	}

	abstract void setUpScene();

	abstract void playScene();

	abstract void terminateScene();
}
