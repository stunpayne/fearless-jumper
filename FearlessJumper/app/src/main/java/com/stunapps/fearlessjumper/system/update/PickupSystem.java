package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.component.transform.Position;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sunny.s on 14/02/18.
 */

@Singleton
public class PickupSystem implements UpdateSystem
{
	private static Map<PickupType, Position> lastPickupPositions;

	private static long lastProcessTime;

	public PickupSystem()
	{

	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();


	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	public enum PickupType
	{
		TIME;
	}
}
