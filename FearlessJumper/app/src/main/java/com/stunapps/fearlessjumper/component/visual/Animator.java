package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.core.State;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Animator extends RenderableComponent<Bitmap>
{
    @Singular
    private Map<AnimationState, Animation> animations = new HashMap<>();
    private StateMachine<AnimationState, AnimationTransition> animationStateMachine;
    private State currentAnimationState;

    public Animator(Map<AnimationState, Animation> animations, Delta delta, float width, float height, StateMachine animationStateMachine)
    {
        super(RenderType.ANIMATOR, delta, width, height);
        this.animations = animations;
        this.animationStateMachine = animationStateMachine;
        this.currentAnimationState = AnimationState.IDLE;
    }

    //  TODO: We need to add
    //  1) Animation States
    //  2) Parameters, Triggers based on Parameters
    //  3) Transitions between states based on Triggers
    //  For now, we will just keep a map of animations and call out which one to play from
    //  the game object

    public void triggerEvent(AnimationTransition event)
    {
        //Log.d(TAG, "triggerEvent: currentAnimationState before state transition = " + animationStateMachine.getCurrentState());
        currentAnimationState = animationStateMachine.transitStateOnEvent(event);
        //Log.d(TAG, "triggerEvent: currentAnimationState = " + animationStateMachine.getCurrentState() + " after eventType = " + eventType);
    }

    public void addAnimation(AnimationState animationName, Animation animation)
    {
        animations.put(animationName, animation);
    }

    private Bitmap playAnimation()
    {
        Animation animation = animations.get(animationStateMachine.getCurrentState());
        //Log.d(TAG, "playAnimation: Animation = "+ animations.keySet());
        //Log.d(TAG, "playAnimation: Current animation = "+animationStateMachine.getCurrentState());
        return animation.play();
    }

    //  We will always reset the animation on stop
    public void stopAnimation()
    {
        Animation animation = animations.get(currentAnimationState);
        if (animation.isPlaying())
            animation.stopAndReset();
        currentAnimationState = null;
    }

    @Override
    public Bitmap getRenderable()
    {
        return playAnimation();
    }

    @Override
    public Animator clone() throws CloneNotSupportedException
    {
        return new Animator(animations, delta, width, height, animationStateMachine.clone());
    }
}
