package com.stunapps.fearlessjumper.audio;

import com.stunapps.fearlessjumper.R;

/**
 * Created by sunny.s on 25/02/18.
 */

public enum Sound
{
	;
	
	public enum Music
	{
		//	Background music
		BACKGROUND_MUSIC(R.raw.second_try);

		private Integer soundResId;

		Music(Integer soundResId)
		{
			this.soundResId = soundResId;
		}

		public Integer getSoundResId()
		{
			return soundResId;
		}
	}

	public enum Effect
	{
		//
		//	//	Game object interaction
		//	PLAYER_HURT,
		//	GAME_OVER,
		TIME_PICKUP(R.raw.pickup);
		//
		//	//	Game state change events
		//	PAUSE_GAME, RESUME_GAME,
		//
		//	//	Notable events
		//	HIGH_SCORE;

		private Integer soundResId;

		Effect(Integer soundResId)
		{
			this.soundResId = soundResId;
		}

		public Integer getSoundResId()
		{
			return soundResId;
		}
	}
}
