package com.stunapps.fearlessjumper.event.impls;

import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.EventType;

/**
 * Created by anand.verma on 15/02/18.
 */

public class SceneStopEvent extends BaseEvent
{
    public SceneStopEvent()
    {
        super(EventType.SCENE_STOP);
    }
}
