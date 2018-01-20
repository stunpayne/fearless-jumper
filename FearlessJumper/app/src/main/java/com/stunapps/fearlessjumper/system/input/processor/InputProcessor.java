package com.stunapps.fearlessjumper.system.input.processor;

import android.view.MotionEvent;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 20/01/18.
 */

public interface InputProcessor
{
    public void process(Entity entity, MotionEvent motionEvent);
}
