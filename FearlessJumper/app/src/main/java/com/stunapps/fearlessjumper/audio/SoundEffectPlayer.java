package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.event.BaseEventListener;

/**
 * Created by sunny.s on 14/02/18.
 */

public interface SoundEffectPlayer
{
	int loadSound(int soundResId, int priority);

	void playSoundEffect(int soundId);

	void stopSoundEffect(int soundId);
}
