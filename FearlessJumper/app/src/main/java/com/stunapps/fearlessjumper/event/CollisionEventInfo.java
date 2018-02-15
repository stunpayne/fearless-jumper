package com.stunapps.fearlessjumper.event;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by sunny.s on 15/02/18.
 */

@Getter
public class CollisionEventInfo extends BaseEventInfo
{
	public Entity entity1;
	public Entity entity2;
	public CollisionFace collisionFace;
	public long deltaTime;

	public CollisionEventInfo(Entity entity1, Entity entity2, CollisionFace collisionFace,
			long deltaTime)
	{
		this.entity1 = entity1;
		this.entity2 = entity2;
		this.collisionFace = collisionFace;
		this.deltaTime = deltaTime;
	}
}
