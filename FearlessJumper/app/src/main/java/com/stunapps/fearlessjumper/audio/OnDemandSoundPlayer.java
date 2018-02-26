package com.stunapps.fearlessjumper.audio;

/**
 * Created by sunny.s on 15/02/18.
 */

public interface OnDemandSoundPlayer
{
	void playSoundEffect(int soundResId);
	void loopSoundEffect(int soundResId);

	void playMusic(int soundResId);
	void loopMusic(int soundResId);
}
