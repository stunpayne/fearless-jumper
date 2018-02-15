package com.stunapps.fearlessjumper.audio;

import android.media.AudioAttributes;
import android.media.MediaPlayer;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.helper.Environment;

/**
 * Created by sunny.s on 15/02/18.
 */

public class MusicPlayerImpl implements MusicPlayer
{
	private MediaPlayer mediaPlayer;

	public MusicPlayerImpl()
	{
		mediaPlayer = MediaPlayer.create(Environment.CONTEXT, R.raw.second_try,
										 new AudioAttributes.Builder()
												 .setContentType(AudioAttributes
																		 .CONTENT_TYPE_MUSIC)
												 .setUsage(AudioAttributes.USAGE_GAME).build(), 1);
	}

	@Override
	public void playMusic(int soundResId)
	{
		mediaPlayer.reset();
		mediaPlayer = MediaPlayer.create(Environment.CONTEXT, soundResId,
										 new AudioAttributes.Builder()
												 .setContentType(AudioAttributes
																		 .CONTENT_TYPE_MUSIC)
												 .setUsage(AudioAttributes.USAGE_GAME).build(), 1);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	@Override
	public void playMusicOnLoop(int soundResId, int numLoops)
	{

	}

	@Override
	public void stopMusic(int soundResId)
	{
		mediaPlayer.stop();
	}
}
