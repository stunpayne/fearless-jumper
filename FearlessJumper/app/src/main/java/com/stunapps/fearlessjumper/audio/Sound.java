package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.R;

/**
 * Created by sunny.s on 25/02/18.
 */

public enum Sound
{
	//	Background music
	BACKGROUND_MUSIC (R.raw.second_try);
//
//	//	Game object interaction
//	PLAYER_HURT, TIME_PICKUP, GAME_OVER,
//
//	//	Game state change events
//	PAUSE_GAME, RESUME_GAME,
//
//	//	Notable events
//	HIGH_SCORE;

	private Integer soundResId;

	Sound(Integer soundResId)
	{
		this.soundResId = soundResId;
	}

	public Integer getSoundResId()
	{
		return soundResId;
	}
}
