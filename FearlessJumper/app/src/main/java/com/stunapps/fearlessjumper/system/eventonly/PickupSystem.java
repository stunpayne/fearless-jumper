package com.stunapps.fearlessjumper.system.eventonly;

import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.CollisionEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.System;

import javax.inject.Singleton;

/**
 * Created by sunny.s on 15/02/18.
 */

@Singleton
public class PickupSystem implements System, BaseEventListener
{
	@Override
	public void handleEvent(Event event, BaseEventInfo eventInfo) throws EventException
	{
		switch (event)
		{
			case COLLISION_DETECTED:
				CollisionEventInfo collision = (CollisionEventInfo) eventInfo;
				break;
		}
	}
}
