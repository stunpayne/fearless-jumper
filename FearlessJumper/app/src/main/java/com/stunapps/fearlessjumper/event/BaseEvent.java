package com.stunapps.fearlessjumper.event;

/**
 * Created by sunny.s on 13/02/18.
 */

abstract public class BaseEvent
{
    public EventType eventType;

    public BaseEvent(EventType eventType)
    {
        this.eventType = eventType;
    }
}
