package com.stunapps.fearlessjumper.animation;

import com.stunapps.fearlessjumper.core.Event;

/**
 * Created by anand.verma on 02/02/18.
 */

public enum AnimationEvent implements Event
{
    STOP,
    TERMINATE,
    TURN_RIGHT,
    TURN_LEFT,
    TURN_UP,
    TURN_DOWN,
    TURN_RIGHT_UP,
    TURN_RIGHT_DOWN,
    TURN_LEFT_UP,
    TURN_LEFT_DOWN,
    HURT
}
