package com.stunapps.fearlessjumper.event.scene;

import com.stunapps.fearlessjumper.event.BaseEvent;

/**
 * Created by anand.verma on 17/02/18.
 */

public class SceneEvent extends BaseEvent
{
    public SceneEvent(Class<? extends BaseEvent> eventType)
    {
        super(eventType);
    }
}
