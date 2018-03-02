package com.stunapps.fearlessjumper;

import com.rits.cloning.Cloner;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.core.StateMachine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by anand.verma on 25/02/18.
 */

public class CloningTest
{
    @Test
    public void testCloning(){
        Cloner cloner = new Cloner();
        StateMachine<AnimationState, AnimationTransition> stateMachine =
                StateMachine.builder().startState(AnimationState.IDLE)
                        .from(AnimationState.IDLE).onEvent(AnimationTransition.TURN_RIGHT)
                        .toState(AnimationState.FLY_RIGHT)
                        .terminalState(AnimationState.FLY_RIGHT).build();

        StateMachine<AnimationState, AnimationTransition> clone1 = null;

        try
        {
            clone1 = stateMachine.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        Assert.assertTrue(stateMachine.getCurrentState() == clone1.getCurrentState());
        Assert.assertTrue(stateMachine.getCurrentState().equals(clone1.getCurrentState()));

        stateMachine.transitStateOnEvent(AnimationTransition.TURN_RIGHT);

        Assert.assertFalse(stateMachine.getCurrentState() == clone1.getCurrentState());
        Assert.assertFalse(stateMachine.getCurrentState().equals(clone1.getCurrentState()));

    }
}
