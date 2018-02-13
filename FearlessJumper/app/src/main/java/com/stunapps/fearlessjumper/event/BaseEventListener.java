package com.stunapps.fearlessjumper.event;

import com.stunapps.fearlessjumper.exception.EventException;

import java.util.EventListener;

/**
 * Created by sunny.s on 13/02/18.
 */

public interface BaseEventListener extends EventListener
{
	void handleEvent(Event event, BaseEventInfo eventInfo) throws EventException;
}
