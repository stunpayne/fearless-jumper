package com.stunapps.fearlessjumper.particle;

import com.stunapps.fearlessjumper.model.Velocity;

/**
 * Created by anand.verma on 06/03/18.
 */

public interface VelocityScaler
{
	Velocity scale(Velocity velocity, float life, float lifeTimer);
}
