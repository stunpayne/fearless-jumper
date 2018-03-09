package com.stunapps.fearlessjumper.event.impl;

import android.util.Log;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.EventException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sunny.s on 13/02/18.
 */

public class GameEventSystem implements EventSystem
{
	private Map<Class<? extends BaseEvent>, List<BaseEventListener>> eventListenersMap = new ConcurrentHashMap<>();

	public GameEventSystem()
	{

	}

	@Override
	public void raiseEvent(BaseEvent event)
	{
		if (eventListenersMap.get(event.eventType) == null)
		{
			eventListenersMap.put(event.eventType, new LinkedList<BaseEventListener>());
		}

		for (BaseEventListener eventListener : eventListenersMap.get(event.eventType))
		{
			try
			{
				eventListener.handleEvent(event);
			}
			catch (EventException e)
			{
				Log.e("EVENT_SYSTEM", "Exception occured while handling eventType: " + e.getMessage());
			}
		}
	}

	@Override
	public void registerEventListener(Class<? extends BaseEvent> eventType, BaseEventListener eventListener)
	{
		if (eventListenersMap.get(eventType) == null)
		{
			eventListenersMap.put(eventType, new LinkedList<BaseEventListener>());
		}
		eventListenersMap.get(eventType).add(eventListener);
	}

	@Override
	public void unregisterEventListener(Class<? extends BaseEvent> eventType, BaseEventListener eventListener)
	{
		if (eventListenersMap.get(eventType).contains(eventListener))
		{
			eventListenersMap.get(eventType).remove(eventListener);
		}
	}

	@Override
	public List<BaseEventListener> getEventListeners(Class<? extends BaseEvent> eventType)
	{
		return eventListenersMap.get(eventType);
	}
}
