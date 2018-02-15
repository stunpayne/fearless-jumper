package com.stunapps.fearlessjumper.audio;

/**
 * Created by sunny.s on 15/02/18.
 */

public interface MusicPlayer
{
	void playMusic(int soundResId);
	void playMusicOnLoop(int soundResId, int numLoops);

	void stopMusic(int soundResId);
}
