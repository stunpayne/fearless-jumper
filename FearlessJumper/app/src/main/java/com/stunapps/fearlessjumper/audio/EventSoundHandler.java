package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;

import javax.inject.Inject;

/**
 * Created by sunny.s on 14/02/18.
 */

public class EventSoundHandler extends AbstractEventSoundHandler
{
	private static final int backGroundMusicResId = R.raw.first_try;

	private final static int PRIORITY_MUSIC = 10;

	@Inject
	public EventSoundHandler(SoundEffectPlayer soundEffectPlayer, EventSystem eventSystem)
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
				playMusic(backGroundMusicResId);
				break;
		}
	}
}
