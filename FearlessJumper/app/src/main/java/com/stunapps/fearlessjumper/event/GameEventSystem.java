package com.stunapps.fearlessjumper.event;

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
			eventListener.handleEvent(event, eventInfo);
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
//		Iterator<BaseEventListener> iterator = eventListenersMap.get(event).iterator();
//		while (iterator.hasNext())
//		{
//			if (iterator.next().equals(eventListener))
//			{
//				iterator.remove();
//			}
//		}
		if(eventListenersMap.get(event).contains(eventListener))
			eventListenersMap.get(event).remove(eventListener);
	}

	@Override
	public List<BaseEventListener> getEventListeners(Event event)
	{
		return eventListenersMap.get(event);
	}
}
