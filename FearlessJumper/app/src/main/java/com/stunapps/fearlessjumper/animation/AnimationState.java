package com.stunapps.fearlessjumper.animation;

import com.stunapps.fearlessjumper.core.State;

/**
 * Created by anand.verma on 02/02/18.
 */

public enum AnimationState implements State
{
    IDLE,
    WALK_RIGHT,
    WALK_LEFT,
    WALK_UP,
    WALK_DOWN,
    WALK_RIGHT_UP,
    WALK_RIGHT_DOWN,
    WALK_LEFT_UP,
    WALK_LEFT_DOWN,
    FLY_RIGHT,
    FLY_LEFT,
    FLY_UP,
    FLY_DOWN,
    FLY_RIGHT_UP,
    FLY_RIGHT_DOWN,
    FLY_LEFT_UP,
    FLY_LEFT_DOWN,
    TERMINATED,
    HURT
}
