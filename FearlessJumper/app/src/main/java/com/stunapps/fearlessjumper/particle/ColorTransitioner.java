package com.stunapps.fearlessjumper.particle;

/**
 * Created by sunny.s on 25/03/18.
 */

public interface ColorTransitioner
{
	int transition(int startColor, int endColor, int currentColor, float life, float lifeTimer);
}
