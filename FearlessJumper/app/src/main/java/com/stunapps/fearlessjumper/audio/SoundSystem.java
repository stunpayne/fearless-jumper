package com.stunapps.fearlessjumper.audio;

import android.util.Log;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.game.Environment;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by sunny.s on 14/02/18.
 */

@Singleton
public class SoundSystem implements OnDemandSoundPlayer
{
	private final static String TAG = "SoundSystem";
	private final SoundEffectPlayer soundEffectPlayer;

	private final static int PRIORITY_MUSIC = 10;
	private final static int PRIORITY_SOUND_EFFECT = 8;

	private Map<Integer, Integer> soundIdMap = Maps.newHashMap();
	private Map<Integer, LoopingMediaPlayer> activeMusicPlayers = Maps.newHashMap();

	@Inject
	public SoundSystem(SoundEffectPlayer soundEffectPlayer)
	{
		this.soundEffectPlayer = soundEffectPlayer;
	}

	public void initialise()
	{
		for (Sound.Effect soundEffect : Sound.Effect.values())
		{
			soundIdMap.put(soundEffect.getSoundResId(), soundEffectPlayer
					.loadSound(soundEffect.getSoundResId(), PRIORITY_SOUND_EFFECT));
		}
	}

	@Override
	public void playSoundEffect(int soundResId)
	{
		Integer soundId = soundIdMap.get(soundResId);
		if (soundId != null) soundEffectPlayer.playSoundEffect(soundId);
		else Log.d(TAG, "Could not find sound: " + soundResId);
	}

	@Override
	public void loopSoundEffect(int soundResId)
	{

	}

	@Override
	public void playMusic(int soundResId)
	{

	}

	@Override
	public void loopMusic(int soundResId)
	{
		LoopingMediaPlayer loopingMediaPlayer =
				LoopingMediaPlayer.create(Environment.CONTEXT, soundResId);
		loopingMediaPlayer.start();
		activeMusicPlayers.put(soundResId, loopingMediaPlayer);
	}

	public void release()
	{
		soundEffectPlayer.release();
	}
}
