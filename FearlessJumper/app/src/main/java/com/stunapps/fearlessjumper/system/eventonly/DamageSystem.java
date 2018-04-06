package com.stunapps.fearlessjumper.system.eventonly;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.event.model.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.model.system.CollisionEvent;
import com.stunapps.fearlessjumper.event.EventException;
import com.stunapps.fearlessjumper.manager.CollisionLayerManager;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.system.System;

/**
 * Created by anand.verma on 03/02/18.
 */

public class DamageSystem implements System
{
	private EventSystem eventSystem;
	private CollisionLayerManager collisionLayerManager;
	private GameStatsManager gameStatsManager;

	@Inject
	public DamageSystem(EventSystem eventSystem, CollisionLayerManager collisionLayerManager,
			GameStatsManager gameStatsManager)
	{
		this.eventSystem = eventSystem;
		this.collisionLayerManager = collisionLayerManager;
		this.gameStatsManager = gameStatsManager;
		eventSystem.registerEventListener(CollisionEvent.class, collisionEventListener);
	}

	private BaseEventListener<CollisionEvent> collisionEventListener = new BaseEventListener<CollisionEvent>()
	{
		@Override
		public void handleEvent(CollisionEvent collisionEvent) throws EventException
		{
			Entity entity1 = collisionEvent.entity1;
			Entity entity2 = collisionEvent.entity2;

			if (entity1.hasComponent(Health.class) && entity2.hasComponent(DamageComponent.class))
			{
				handleDamage(entity1, entity2, collisionEvent);
				handleSelfDestruct(entity2);
			}

			if (entity2.hasComponent(Health.class) && entity1.hasComponent(DamageComponent.class))
			{
				handleDamage(entity2, entity1, collisionEvent);
				handleSelfDestruct(entity1);
			}
		}

		private void handleDamage(Entity damaged, Entity damaging, CollisionEvent collisionEvent)
		{
			DamageComponent damageComponent = damaging.getComponent(DamageComponent.class);
			ContactDamageComponent contactDamageComponent = (ContactDamageComponent) damageComponent;
			Log.d("DamageSystem",
				  "handleDamage: collisionEvent.collisionFace = " + collisionEvent.collisionFace +
						  ", getDamageRespondingFaces = " +
						  contactDamageComponent.getDamageRespondingFaces());
			if (damageComponent instanceof ContactDamageComponent)
			{
				if (contactDamageComponent.getDamageRespondingFaces()
						.contains(collisionEvent.collisionFace))
				{
					handleContactDamage(damaged, damaging, damageComponent);
				}
			}
		}

		private void handleSelfDestruct(Entity entity)
		{
			if (entity.getComponent(DamageComponent.class).selfDestructOnContact)
			{
				entity.delete();
			}
		}
	};

	private void handleContactDamage(Entity damaged, Entity damaging,
			DamageComponent damageComponent)
	{
		Animator animator = damaged.getComponent(Animator.class);
		Health health = damaged.getComponent(Health.class);
		health.takeDamage(damageComponent.damage());
		if (health.isOver())
		{
			animator.triggerTransition(AnimationTransition.TERMINATE);
			eventSystem.raiseEvent(new GameOverEvent());
			if (damaging.hasComponent(Enemy.class))
			{
				gameStatsManager.updateDeathStat(
						damaging.getComponent(Enemy.class).getEnemyType().name());
			}
		}
		else
		{
			Collider collider = damaged.getComponent(Collider.class);
			Collider collidesWith = damaging.getComponent(Collider.class);
			collisionLayerManager.timedFlipCollisionLayerMask(collider.collisionLayer,
															  collidesWith.collisionLayer,
															  1000l);
			animator.triggerTransition(AnimationTransition.HURT);
			if (damaging.hasComponent(Enemy.class))
			{
				gameStatsManager.updateHurtStat(
						damaging.getComponent(Enemy.class).getEnemyType().name());
			}
		}
	}


}
