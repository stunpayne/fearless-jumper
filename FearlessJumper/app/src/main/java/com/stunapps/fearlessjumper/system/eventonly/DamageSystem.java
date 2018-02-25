package com.stunapps.fearlessjumper.system.eventonly;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impl.HurtEvent;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.system.CollisionEvent;

import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.System;

/**
 * Created by anand.verma on 03/02/18.
 */

public class DamageSystem implements System
{
	private EventSystem eventSystem;

	private BaseEventListener<CollisionEvent> collisionEventListener = new BaseEventListener<CollisionEvent>()
	{
		@Override
		public void handleEvent(CollisionEvent collisionEvent) throws EventException
		{
			Entity entity1 = collisionEvent.entity1;
			Entity entity2 = collisionEvent.entity2;

			Health health1 = entity1.getComponent(Health.class);
			DamageComponent damageComponent1 = entity1.getComponent(DamageComponent.class);

			Health health2 = entity2.getComponent(Health.class);
			DamageComponent damageComponent2 = entity2.getComponent(DamageComponent.class);

			if (health1 != null && damageComponent2 != null)
			{
				eventSystem.raiseEvent(new HurtEvent());
				Animator animator = entity1.getComponent(Animator.class);
				health1.takeDamage(damageComponent2.damage());
				if (health1.isOver())
				{
					animator.triggerEvent(AnimationTransition.TERMINATE);
					eventSystem.raiseEvent(new GameOverEvent());
				}
				else
				{
					animator.triggerEvent(AnimationTransition.HURT);
				}
			}

			if (health2 != null && damageComponent1 != null)
			{
				eventSystem.raiseEvent(new HurtEvent());
				Animator animator = entity2.getComponent(Animator.class);
				health2.takeDamage(damageComponent1.damage());
				if (health2.isOver())
				{
					animator.triggerEvent(AnimationTransition.TERMINATE);
					eventSystem.raiseEvent(new GameOverEvent());
				}
				else
				{
					animator.triggerEvent(AnimationTransition.HURT);
				}
			}
		}
	};

	@Inject
	public DamageSystem(EventSystem eventSystem)
	{
		this.eventSystem = eventSystem;
		eventSystem.registerEventListener(CollisionEvent.class, collisionEventListener);
		this.eventSystem = eventSystem;
	}
}
