package com.stunapps.fearlessjumper.event;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by sunny.s on 15/02/18.
 */

@Getter
@AllArgsConstructor
public class CollisionEventInfo extends BaseEventInfo
{
	public Entity entity1;
	public Entity entity2;
	public CollisionFace collisionFace;
}
