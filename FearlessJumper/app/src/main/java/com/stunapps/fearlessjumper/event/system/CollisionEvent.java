package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

/**
 * Created by sunny.s on 15/02/18.
 */

@Getter
public class CollisionEvent extends SystemEvent
{
	public Entity entity1;
	public Entity entity2;
	public CollisionFace collisionFace;
	public long deltaTime;

	public CollisionEvent(Entity entity1, Entity entity2, CollisionFace collisionFace,
						  long deltaTime)
	{
		super(CollisionEvent.class);
		this.entity1 = entity1;
		this.entity2 = entity2;
		this.collisionFace = collisionFace;
		this.deltaTime = deltaTime;
	}

	public Set<Entity> getCollidingEntities()
	{
		return new HashSet<>(Arrays.asList(entity1, entity2));
	}
}
