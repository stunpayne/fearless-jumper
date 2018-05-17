package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.core.StateMachine;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

/**
 * Created by sunny.s on 03/01/18.
 */

public class BitmapAnimator extends Component implements Animator<Bitmap>
{
	@Singular
	private Map<AnimationState, Animation> animations = new HashMap<>();
	private StateMachine<AnimationState, AnimationTransition> animationStateMachine;

	public BitmapAnimator(Map<AnimationState, Animation> animations, StateMachine animationStateMachine)
	{
		super(BitmapAnimator.class);
		this.animations = animations;
		this.animationStateMachine = animationStateMachine;
	}

	public void triggerTransition(AnimationTransition event)
	{
		//Log.d(TAG, "triggerTransition: currentAnimationState before state transition = " +
		// animationStateMachine.getCurrentState());
		animationStateMachine.transitStateOnEvent(event);
		//Log.d(TAG, "triggerTransition: currentAnimationState = " + animationStateMachine
        // .getCurrentState() + " after eventType = " + eventType);
	}

	public void addAnimation(AnimationState animationName, Animation animation)
	{
		animations.put(animationName, animation);
	}

	private Bitmap playAnimation()
	{
		Animation animation = animations.get(animationStateMachine.getCurrentState());
		return animation.play();
	}

	//  We will always reset the animation on stop
	public void stopAnimation()
	{
		Animation animation = animations.get(animationStateMachine.getCurrentState());
		if (animation.isPlaying()) animation.stopAndReset();
	}


	public Bitmap update()
	{
		return playAnimation();
	}

	@Override
	public BitmapAnimator cloneComponent() throws CloneNotSupportedException
	{
		return new BitmapAnimator(animations, animationStateMachine.clone());
	}
}
