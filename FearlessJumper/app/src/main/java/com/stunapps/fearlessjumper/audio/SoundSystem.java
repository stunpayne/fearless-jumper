package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by sunny.s on 14/02/18.
 */

public class SoundSystem extends AbstractEventSoundHandler implements OnDemandSoundPlayer
{
	private static final int backGroundMusicResId = R.raw.second_try;

	private final static int PRIORITY_MUSIC = 10;
	private final static int PRIORITY_SOUND_EFFECT = 8;

	private Map<Integer, List<Integer>> sceneMusicMap;
	private Map<Integer, List<Integer>> sceneSoundMap;
	private Integer currentScene;

	@Inject
	public SoundSystem(SoundEffectPlayer soundEffectPlayer, EventSystem eventSystem)
	{
		super(soundEffectPlayer);
		BACKGROUND_MUSIC_ID = soundEffectPlayer.loadSound(backGroundMusicResId, PRIORITY_MUSIC);
		eventSystem.registerEventListener(Event.START_GAME, this);
	}

	public static int BACKGROUND_MUSIC_ID;

	@Override
	void playSoundOnEvent(Event event, BaseEventInfo eventInfo)
	{
		switch (event)
		{
			case START_GAME:
				break;
		}
	}

	@Override
	public void playOnDemand(int soundResId)
	{

	}

	@Override
	public void loopOnDemand(int soundResId)
	{

	}

	public void stopSceneMusic(int sceneId)
	{
		//	TODO: This is incorrect. This needs to be changed.
		soundEffectPlayer.stopSoundEffect(sceneId);
	}
}
