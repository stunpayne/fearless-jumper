package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.helper.Environment;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by sunny.s on 14/02/18.
 */

public class SoundSystem implements OnDemandSoundPlayer
{
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
		for (Sound sound : Sound.values())
		{
			soundIdMap.put(sound.getSoundResId(),
					soundEffectPlayer.loadSound(sound.getSoundResId(), PRIORITY_SOUND_EFFECT));
		}
	}

	public void playMusic()
	{

	}

	@Override
	public void playSoundEffect(int soundResId)
	{
		Integer soundId = soundIdMap.get(soundResId);
		if (soundId != null) soundEffectPlayer.playSoundEffect(soundId);
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
	public void loopMusic(Sound sound)
	{
		LoopingMediaPlayer loopingMediaPlayer =
				LoopingMediaPlayer.create(Environment.CONTEXT, sound.getSoundResId());
		loopingMediaPlayer.start();
		activeMusicPlayers.put(sound.getSoundResId(), loopingMediaPlayer);
	}

	public void release()
	{
		soundEffectPlayer.release();
	}
}
