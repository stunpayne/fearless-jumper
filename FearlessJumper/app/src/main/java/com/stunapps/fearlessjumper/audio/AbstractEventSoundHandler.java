package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.exception.EventException;

/**
 * Created by sunny.s on 14/02/18.
 */

public abstract class AbstractEventSoundHandler implements BaseEventListener
{
	protected final SoundEffectPlayer soundEffectPlayer;

	public AbstractEventSoundHandler(SoundEffectPlayer soundEffectPlayer)
	{
		this.soundEffectPlayer = soundEffectPlayer;
	}

	@Override
	public final void handleEvent(BaseEvent event) throws EventException
	{
		playSoundOnEvent(event);
	}

	protected void playSoundEffect(int soundId)
	{
		soundEffectPlayer.playSoundEffect(soundId);
	}

	abstract void playSoundOnEvent(BaseEvent event);
}
