package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.animation.BaseShapeAnimation;
import com.stunapps.fearlessjumper.animation.ShapeAnimation;
import com.stunapps.fearlessjumper.animation.ShapeColorAnimation;
import com.stunapps.fearlessjumper.animation.ShapeSizeAnimation;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.core.StateMachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Singular;

/**
 * Created by sunny.s on 11/04/18.
 */

public class ShapeAnimator extends Component implements Animator<List<Shape>>
{
	@Singular
	private Map<AnimationState, ShapeAnimation> animations = new HashMap<>();
	private StateMachine<AnimationState, AnimationTransition> animationStateMachine;

	public ShapeAnimator(Map<AnimationState, ShapeAnimation> animations, StateMachine
			animationStateMachine)
	{
		super(ShapeAnimator.class);
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

	public void addAnimation(AnimationState animationName, ShapeAnimation animation)
	{
		animations.put(animationName, animation);
	}

	private List<Shape> playAnimation()
	{
		ShapeAnimation animation = animations.get(animationStateMachine.getCurrentState());
		return animation.play();
	}

	//  We will always reset the animation on stop
	public void stopAnimation()
	{
		ShapeAnimation animation = animations.get(animationStateMachine.getCurrentState());
		if (animation.isPlaying()) animation.stopAndReset();
	}

	public List<Shape> update()
	{
		return playAnimation();
	}

	@Override
	public ShapeAnimator clone() throws CloneNotSupportedException
	{
		return new ShapeAnimator(animations, animationStateMachine.clone());
	}
}
