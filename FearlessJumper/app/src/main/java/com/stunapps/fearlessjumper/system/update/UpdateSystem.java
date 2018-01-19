package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.system.System;

/**
 * Created by sunny.s on 19/01/18.
 */

public interface UpdateSystem extends System
{
    public void process(long deltaTime);
}
