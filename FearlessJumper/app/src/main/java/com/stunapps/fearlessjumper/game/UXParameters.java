package com.stunapps.fearlessjumper.game;

/**
 * Created by sunny.s on 14/02/18.
 */

public class UXParameters
{
	public static long ASSAULT_SMILEY_WAIT_TIME;
	public static float ASSAULT_SMILEY_ATTACK_SPEED;

	static
	{
		initialise();
	}

	public static void reset()
	{
		initialise();
	}

	private static void initialise()
	{
		ASSAULT_SMILEY_WAIT_TIME = InitialParameters.ASSAULT_SMILEY_WAIT_TIME;
		ASSAULT_SMILEY_ATTACK_SPEED = InitialParameters.ASSAULT_SMILEY_ATTACK_SPEED;
	}

	public static void tuneAssaultSmileyAttackSpeed(float delta)
	{
		ASSAULT_SMILEY_ATTACK_SPEED += delta;
	}

	public static void tuneAssaultSmileyWaitTime(long delta)
	{
		ASSAULT_SMILEY_ATTACK_SPEED += delta;
	}

	private static class InitialParameters
	{
		private static long ASSAULT_SMILEY_WAIT_TIME = 1200;
		private static float ASSAULT_SMILEY_ATTACK_SPEED = 200f;
	}
}
