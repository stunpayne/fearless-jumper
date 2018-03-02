package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.core.StateMachine;

import org.junit.Test;

import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationState.HURT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationState.TERMINATED;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.NORMALIZE;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.STOP;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.TERMINATE;

/**
 * Created by sunny.s on 25/02/18.
 */

public class AnyStateTest
{
	@Test
	public void testAnyState()
	{
		StateMachine stateMachineWithoutAny =
				StateMachine.builder()
						.startState(IDLE)
						.from(IDLE).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onEvent(TERMINATE).toState(TERMINATED)
						.from(IDLE).onEvent(TERMINATE).toState(TERMINATED)
						.from(HURT).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onCountDown(16l).toState(IDLE)
						.build();
		System.out.println(stateMachineWithoutAny);

		StateMachine stateMachineWithAnyBefore =
				StateMachine.builder()
						.startState(IDLE)
						.from(IDLE).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onCountDown(16l).toState(IDLE)
						.build();
		System.out.println(stateMachineWithAnyBefore);

		StateMachine stateMachineWithAnyAfter =
				StateMachine.builder()
						.startState(IDLE)
						.from(IDLE).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onEvent(AnimationTransition.HURT).toState(HURT)
						.fromAnyStateOnEvent(TERMINATE).toState(TERMINATED)
						.fromAnyStateOnEvent(STOP).toState(FLY_DOWN)
						.fromAnyStateOnEvent(NORMALIZE).toState(IDLE)
						.from(HURT).onCountDown(16l).toState(IDLE)
						.terminalState(TERMINATED)
						.build();
		System.out.println(stateMachineWithAnyAfter);
	}
}
