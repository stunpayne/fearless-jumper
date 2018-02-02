package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.core.FiniteStateMachine;
import com.stunapps.fearlessjumper.core.State;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_DOWN;

/**
 * Created by sunny.s on 03/01/18.
 */

public class AnimatorComponent extends RenderableComponent<Bitmap>
{
    @Singular
    private Map<AnimationState, Animation> animations = new HashMap<>();
    private FiniteStateMachine animationStateMachine;
    private State currentAnimationState;

    public AnimatorComponent(Map<AnimationState, Animation> animations, Delta delta, float width, float height, FiniteStateMachine animationStateMachine)
    {
        super(RenderType.ANIMATOR, delta, width, height);
        this.animations = animations;
        this.animationStateMachine = animationStateMachine;
        this.currentAnimationState = AnimationState.FLY_RIGHT;
    }

    //  TODO: We need to add
    //  1) Animation States
    //  2) Parameters, Triggers based on Parameters
    //  3) Transitions between states based on Triggers
    //  For now, we will just keep a map of animations and call out which one to play from
    //  the game object

    public void triggerEvent(AnimationEvent event)
    {
        currentAnimationState = animationStateMachine.transitStateOnEvent(event);
    }

    public void addAnimation(AnimationState animationName, Animation animation)
    {
        animations.put(animationName, animation);
    }

    private Bitmap playAnimation()
    {
        Animation animation = animations.get(currentAnimationState);
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
