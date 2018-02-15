package com.stunapps.fearlessjumper.event.impls;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.EventType;

/**
 * Created by anand.verma on 15/02/18.
 */

public class SceneStartEvent extends BaseEvent
{
    public SceneStartEvent()
    {
        super(EventType.SCENE_START);
    }
}
