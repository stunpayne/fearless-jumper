package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;
import android.util.Log;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.core.State;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

import static android.content.ContentValues.TAG;

/**
 * Created by sunny.s on 03/01/18.
 */

public class AnimatorComponent extends RenderableComponent<Bitmap>
{
    @Singular
    private Map<AnimationState, Animation> animations = new HashMap<>();
    private StateMachine animationStateMachine;
    private State currentAnimationState;

    public AnimatorComponent(Map<AnimationState, Animation> animations, Delta delta, float width, float height, StateMachine animationStateMachine)
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

    public void triggerEvent(AnimationEvent event)
    {
        //Log.d(TAG, "triggerEvent: currentAnimationState before state transition = " + animationStateMachine.getCurrentState());
        currentAnimationState = animationStateMachine.transitStateOnEvent(event);
        //Log.d(TAG, "triggerEvent: currentAnimationState = " + animationStateMachine.getCurrentState() + " after event = " + event);
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
}
