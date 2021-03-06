package com.stunapps.fearlessjumper.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.media.SoundPool.Builder;

import com.stunapps.fearlessjumper.game.Environment;

/**
 * @author sunny.s
 * @since 14/02/18
 * An implementation of the SoundEffectPlayer interface.
 * Music and sound effect have been separated because they are of different priorities.
 * Music, such as background music, has a higher priority than sound effects, such as that of
 * pickups.
 *
 * @see <a href="https://developer.android.com/reference/android/media/SoundPool.html">SoundPool</a>
 * Parameter explanation:
 * maxStreams - Maximum number of parallel streams that can be played by this sound pool. If this
 * number is crossed at any time, the sound pool will stop the oldest amongst the lowest
 * priority sounds. If the priority of the new sound to be played is less than the least of the
 * sounds currently being played, the new sound will not play.
 *
 * Note: SoundPool can play a sound of maximum size 1MB. For larger files, see MusicPlayer.
 * @see MusicPlayer
 * @see MusicPlayerImpl
 */
public class SoundEffectPlayerImpl implements SoundEffectPlayer
{
	private static final float LEFT_VOLUME = 1.0f;
	private static final float RIGHT_VOLUME = 1.0f;
	private static final int SOUND_EFFECT_PRIORITY = 10;

	private SoundPool soundPool;

	public SoundEffectPlayerImpl()
	{
		initSoundPool();
	}

	@Override
	public int loadSound(int soundResId, int priority)
	{
		return soundPool.load(Environment.CONTEXT, soundResId, priority);
	}

	@Override
	public void playSoundEffect(int soundId)
	{
		soundPool.play(soundId, LEFT_VOLUME, RIGHT_VOLUME, SOUND_EFFECT_PRIORITY, 0, 1.0f);
	}

	@Override
	public void stopSoundEffect(int soundId)
	{
		soundPool.stop(soundId);
	}

	@Override
	public void release()
	{
		soundPool.release();
	}

	private void initSoundPool()
	{
		SoundPool.Builder soundPoolBuilder = new Builder();
		soundPoolBuilder.setMaxStreams(3);
		soundPoolBuilder.setAudioAttributes(new AudioAttributes.Builder().setContentType(
				AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_GAME)
													.build());

		soundPool = soundPoolBuilder.build();
	}
}
