package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.event.BaseEvent;

/**
 * Created by anand.verma on 17/02/18.
 */

public class SystemEvent extends BaseEvent
{
    public SystemEvent(Class<? extends BaseEvent> eventType)
    {
        super(eventType);
    }
}
