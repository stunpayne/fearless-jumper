package com.stunapps.fearlessjumper;

import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.IdRes;
import android.util.Log;

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
	public void testCloning()
	{
		Cloner cloner = new Cloner();
		StateMachine<AnimationState, AnimationTransition> stateMachine =
				StateMachine.builder().startState(AnimationState.IDLE).from(AnimationState.IDLE)
						.onEvent(AnimationTransition.TURN_RIGHT).toState(AnimationState.FLY_RIGHT)
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

	@Test
	public void enumTest()
	{
		Mode enumValue = getEnumValue("ADD", Mode.class, Mode.ADD);
	}

	private <C extends Enum<C>> C getEnumValue(String s, Class<C> enumClass, C defaultValue)
	{
		if (s == null) return defaultValue;
		try
		{
			return Enum.valueOf(enumClass, s);
		}
		catch (IllegalArgumentException var4)
		{
			Log.d("Exception Type =  " + enumType.getName() + " and value = " + name, var4);
		}
		catch (NullPointerException var5)
		{
			Log.d("Exception Type =  " + enumType + " and value = " + name, var5);
		}
		return defaultValue;
	}
}
