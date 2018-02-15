package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.exception.EventException;

/**
 * Created by sunny.s on 14/02/18.
 */

public abstract class AbstractEventSoundHandler implements BaseEventListener
{
	protected final SoundEffectPlayer soundEffectPlayer;

	public AbstractEventSoundHandler(SoundEffectPlayer soundManager)
	{
		this.soundEffectPlayer = soundManager;
	}

	@Override
	public final void handleEvent(Event event, BaseEventInfo eventInfo) throws EventException
	{
		playSoundOnEvent(event, eventInfo);
	}

	protected void playMusic(int soundId)
	{
		soundEffectPlayer.playMusic(soundId);
	}

	protected void playSoundEffect(int soundId)
	{
		soundEffectPlayer.playSoundEffect(soundId);
	}

	abstract void playSoundOnEvent(Event event, BaseEventInfo eventInfo);
}
