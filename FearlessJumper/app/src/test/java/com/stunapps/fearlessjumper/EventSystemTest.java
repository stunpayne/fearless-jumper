package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.GameEventSystem;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sunny.s on 13/02/18.
 */

public class EventSystemTest
{
	EventSystem eventSystem = new GameEventSystem();

	@Test
	public void testRegisterUnregister()
	{
		Event testEvent = Event.START_GAME;
		TestEventListener eventListener = new TestEventListener();

		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Initial List already contains event listener",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener));
		eventSystem.registerEventListener(testEvent, eventListener);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Event listener not registered",
						  eventSystem.getEventListeners(testEvent).contains(eventListener));
		eventSystem.unregisterEventListener(testEvent, eventListener);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Event listener not unregistered",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener));
	}

	@Test
	public void testRaise()
	{
		Event testEvent = Event.START_GAME;
		TestEventListener eventListener = new TestEventListener();

		System.out.println(eventSystem.getEventListeners(testEvent));
		System.out.println("Event raised before: " + eventListener.hasEventBeenRaised());
		Assert.assertTrue("Event has already been raised!", !eventListener.hasEventBeenRaised());

		eventSystem.registerEventListener(testEvent, eventListener);
		eventSystem.raiseEvent(testEvent, new BaseEventInfo());
		System.out.println("Event raised after: " + eventListener.hasEventBeenRaised());

		Assert.assertTrue("Event has not been raised!", eventListener.hasEventBeenRaised());
	}

	class TestEventListener implements BaseEventListener
	{
		public boolean eventRaised = false;

		@Override
		public void handleEvent(Event event, BaseEventInfo eventInfo)
		{
			eventRaised = true;
		}

		public boolean hasEventBeenRaised()
		{
			return eventRaised;
		}
	}
}
