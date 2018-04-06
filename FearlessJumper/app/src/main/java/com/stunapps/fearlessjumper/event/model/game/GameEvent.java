package com.stunapps.fearlessjumper.event.model.game;

import com.stunapps.fearlessjumper.event.model.BaseEvent;

/**
 * Created by anand.verma on 17/02/18.
 */

public class GameEvent extends BaseEvent
{
    public GameEvent(Class<? extends BaseEvent> eventType)
    {
        super(eventType);
    }
}
