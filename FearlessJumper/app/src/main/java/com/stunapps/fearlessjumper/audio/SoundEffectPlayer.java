package com.stunapps.fearlessjumper.audio;

/**
 * Created by sunny.s on 14/02/18.
 *
 * soundResId is the resource ID.
 * soundId is the ID used by the SoundEffectPlayer to recognize the sound.
 *
 * @see SoundEffectPlayerImpl
 */

public interface SoundEffectPlayer
{
	int loadSound(int soundResId, int priority);

	void playSoundEffect(int soundId);

	void stopSoundEffect(int soundId);

	void release();
}
