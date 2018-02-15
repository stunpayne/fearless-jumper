package com.stunapps.fearlessjumper.event.impls;

import android.util.Log;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.EventType;
import com.stunapps.fearlessjumper.exception.EventException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sunny.s on 13/02/18.
 */

public class GameEventSystem implements EventSystem
{
	private Map<EventType, List<BaseEventListener>> eventListenersMap = new ConcurrentHashMap<>();

	public GameEventSystem()
	{
		for (EventType eventType : EventType.values())
		{
			eventListenersMap.put(eventType, new ArrayList<BaseEventListener>());
		}
	}

	@Override
	public void raiseEvent(BaseEvent event)
	{
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
	public void registerEventListener(EventType eventType, BaseEventListener eventListener)
	{
		eventListenersMap.get(eventType).add(eventListener);
	}

	@Override
	public void unregisterEventListener(EventType eventType, BaseEventListener eventListener)
	{
		if (eventListenersMap.get(eventType).contains(eventListener))
			eventListenersMap.get(eventType).remove(eventListener);
	}

	@Override
	public List<BaseEventListener> getEventListeners(EventType eventType)
	{
		return eventListenersMap.get(eventType);
	}
}
