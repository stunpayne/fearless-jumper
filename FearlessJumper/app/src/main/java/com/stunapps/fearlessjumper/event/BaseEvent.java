package com.stunapps.fearlessjumper.event;

/**
 * Created by sunny.s on 13/02/18.
 */

abstract public class BaseEvent
{
    public Class<? extends BaseEvent> eventType;

    public BaseEvent(Class<? extends BaseEvent> eventType)
    {
        this.eventType = eventType;
    }
}
