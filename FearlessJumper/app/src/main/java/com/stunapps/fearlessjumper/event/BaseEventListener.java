package com.stunapps.fearlessjumper.event;

import com.stunapps.fearlessjumper.event.model.BaseEvent;

import java.util.EventListener;

/**
 * Created by sunny.s on 13/02/18.
 */

public interface BaseEventListener<E extends BaseEvent> extends EventListener
{
	void handleEvent(E event) throws EventException;
}
