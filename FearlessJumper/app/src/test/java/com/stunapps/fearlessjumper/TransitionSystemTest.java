package com.stunapps.fearlessjumper;

import android.util.Log;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventType;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impls.CollisionEvent;
import com.stunapps.fearlessjumper.event.impls.GameEventSystem;
import com.stunapps.fearlessjumper.event.impls.StartGameEvent;
import com.stunapps.fearlessjumper.exception.EventException;

import org.junit.Assert;
import org.junit.Test;

import static android.content.ContentValues.TAG;

/**
 * Created by sunny.s on 13/02/18.
 */

public class TransitionSystemTest
{
	EventSystem eventSystem = new GameEventSystem();

	@Test
	public void testRegisterUnregister()
	{
		EventType testEvent = EventType.START_GAME;
		TestEventListener eventListener = new TestEventListener();
		TestEventListener eventListener2 = new TestEventListener();
		TestEventListener eventListener3 = new TestEventListener();

		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Initial List already contains eventType listener",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener));
		Assert.assertTrue("Initial List already contains eventType listener 2",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener2));

		eventSystem.registerEventListener(testEvent, eventListener);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Transition listener not registered",
						  eventSystem.getEventListeners(testEvent).contains(eventListener));
		Assert.assertTrue("Transition listener 2 registered",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener2));

		eventSystem.registerEventListener(testEvent, eventListener2);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Transition listener not registered",
						  eventSystem.getEventListeners(testEvent).contains(eventListener));
		Assert.assertTrue("Transition listener 2 not registered",
						  eventSystem.getEventListeners(testEvent).contains(eventListener2));


		eventSystem.unregisterEventListener(testEvent, eventListener2);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Transition listener 1 unregistered",
						  eventSystem.getEventListeners(testEvent).contains(eventListener));
		Assert.assertTrue("Transition listener 2 not unregistered",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener2));

		eventSystem.unregisterEventListener(testEvent, eventListener);
		System.out.println(eventSystem.getEventListeners(testEvent));
		Assert.assertTrue("Transition listener not unregistered",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener));
		Assert.assertTrue("Transition listener 2 unregistered",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener2));

		eventSystem.unregisterEventListener(testEvent, eventListener3);
		Assert.assertTrue("Transition listener 3 unregister failed",
						  !eventSystem.getEventListeners(testEvent).contains(eventListener3));
	}

	@Test
	public void testRaise()
	{
		EventType testEvent = EventType.START_GAME;
		TestEventListener eventListener = new TestEventListener();
		TestEventListener eventListener2 = new TestEventListener();

		System.out.println(eventSystem.getEventListeners(testEvent));
		System.out.println("Transition raised before: " + eventListener.hasEventBeenRaised());
		Assert.assertTrue("Transition has already been raised!", !eventListener.hasEventBeenRaised());

		eventSystem.registerEventListener(testEvent, eventListener);
		eventSystem.raiseEvent(new StartGameEvent());
		System.out.println("Transition 1 raised after: " + eventListener.hasEventBeenRaised());
		System.out.println("Transition 2 raised after: " + eventListener2.hasEventBeenRaised());
		Assert.assertTrue("Transition has not been raised!", eventListener.hasEventBeenRaised());
		Assert.assertTrue("Transition 2 has been raised!", !eventListener2.hasEventBeenRaised());

		eventSystem.registerEventListener(testEvent, eventListener2);
		eventSystem.raiseEvent(new StartGameEvent());
		System.out.println("Transition 1 raised after: " + eventListener.hasEventBeenRaised());
		System.out.println("Transition 2 raised after: " + eventListener2.hasEventBeenRaised());
		Assert.assertTrue("Transition has not been raised!", eventListener.hasEventBeenRaised());
		Assert.assertTrue("Transition 2 has not been raised!", eventListener2.hasEventBeenRaised());
	}

	class TestEventListener implements BaseEventListener
	{
		public boolean eventRaised = false;

		@Override
		public void handleEvent(BaseEvent event)
		{
			eventRaised = true;
		}

		public boolean hasEventBeenRaised()
		{
			return eventRaised;
		}
	}

	@Test
	public void testRaiseV2(){
		TestEventListnerV2 listnerV2 = new TestEventListnerV2();
		System.out.println("starting test");
		eventSystem.raiseEvent(new StartGameEvent());
		eventSystem.raiseEvent(new CollisionEvent(null, null, null, 0));
	}

	class TestEventListnerV2 {

		BaseEventListener<StartGameEvent> gameEventListner = new BaseEventListener<StartGameEvent>()
		{
			@Override
			public void handleEvent(StartGameEvent event) throws EventException
			{
				System.out.println("StartGameEvent event received");
			}
		};

		BaseEventListener<CollisionEvent> collisionEventListner = new BaseEventListener<CollisionEvent>()
		{
			@Override
			public void handleEvent(CollisionEvent event) throws EventException
			{
				System.out.println("CollisionEvent event received");
			}
		};

		public TestEventListnerV2()
		{
			eventSystem.registerEventListener(EventType.START_GAME, gameEventListner);
			eventSystem.registerEventListener(EventType.COLLISION_DETECTED, collisionEventListner);
		}
	}

}
