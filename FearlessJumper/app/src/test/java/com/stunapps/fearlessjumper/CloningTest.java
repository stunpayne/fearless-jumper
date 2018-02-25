package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.core.StateMachine;

import org.junit.Test;

/**
 * Created by anand.verma on 25/02/18.
 */

public class CloningTest
{
    @Test
    public void testCloning(){
        StateMachine<AnimationState, AnimationTransition> stateMachine = StateMachine.builder().startState(AnimationState.IDLE).from(AnimationState.FLY_RIGHT).onEvent(AnimationTransition.TURN_RIGHT).toState(AnimationState.FLY_RIGHT).build();


    }
}
