package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.model.BaseEvent;

import java.util.List;

/**
 * Created by sunny.s on 13/02/18.
 */

public interface EventSystem
{
	void raiseEvent(BaseEvent event);
	void registerEventListener(Class<? extends BaseEvent> eventType, BaseEventListener eventListener);
	void unregisterEventListener(Class<? extends BaseEvent> eventType, BaseEventListener eventListener);
	List<BaseEventListener> getEventListeners(Class<? extends BaseEvent> eventType);
}
