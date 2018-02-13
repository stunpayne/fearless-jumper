package com.stunapps.fearlessjumper.event;

import java.util.List;

/**
 * Created by sunny.s on 13/02/18.
 */

public interface EventSystem
{
	void raiseEvent(Event event, BaseEventInfo eventInfo);
	void registerEventListener(Event event, BaseEventListener eventListener);
	void unregisterEventListener(Event event, BaseEventListener eventListener);
	List<BaseEventListener> getEventListeners(Event event);
}
