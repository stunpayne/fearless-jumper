package com.stunapps.fearlessjumper.event;

import java.util.List;

/**
 * Created by sunny.s on 13/02/18.
 */

public interface EventSystem
{
	void raiseEvent(BaseEvent event);
	void registerEventListener(EventType event, BaseEventListener eventListener);
	void unregisterEventListener(EventType event, BaseEventListener eventListener);
	List<BaseEventListener> getEventListeners(EventType event);
}
