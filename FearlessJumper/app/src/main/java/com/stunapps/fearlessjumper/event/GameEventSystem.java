package com.stunapps.fearlessjumper.event;

import android.util.Log;

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
	private Map<Event, List<BaseEventListener>> eventListenersMap = new ConcurrentHashMap<>();

	public GameEventSystem()
	{
		for (Event event : Event.values())
		{
			eventListenersMap.put(event, new ArrayList<BaseEventListener>());
		}
	}

	@Override
	public void raiseEvent(Event event, BaseEventInfo eventInfo)
	{
		for (BaseEventListener eventListener : eventListenersMap.get(event))
		{
			try
			{
				eventListener.handleEvent(event, eventInfo);
			}
			catch (EventException e)
			{
				Log.e("EVENT_SYSTEM", "Exception occured while handling event: " + e.getMessage());
			}
		}
	}

	@Override
	public void registerEventListener(Event event, BaseEventListener eventListener)
	{
		eventListenersMap.get(event).add(eventListener);
	}

	@Override
	public void unregisterEventListener(Event event, BaseEventListener eventListener)
	{
		if (eventListenersMap.get(event).contains(eventListener))
			eventListenersMap.get(event).remove(eventListener);
	}

	@Override
	public List<BaseEventListener> getEventListeners(Event event)
	{
		return eventListenersMap.get(event);
	}
}
