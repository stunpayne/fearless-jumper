package com.stunapps.fearlessjumper.audio;

/**
 * Created by sunny.s on 15/02/18.
 */

public interface OnDemandSoundPlayer
{
	void playOnDemand(int soundResId);
	void loopOnDemand(int soundResId);
}
