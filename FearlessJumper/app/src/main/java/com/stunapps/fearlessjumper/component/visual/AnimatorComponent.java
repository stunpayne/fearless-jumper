package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.component.Component;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

/**
 * Created by sunny.s on 03/01/18.
 */

public class AnimatorComponent extends Component
{
    @Singular
    private Map<String, Animation> animations = new HashMap<>();

    //  TODO: We need to add
    //  1) Animation States
    //  2) Parameters, Triggers based on Parameters
    //  3) Transitions between states based on Triggers
    //  For now, we will just keep a map of animations and call out which one to play from
    //  the game object

    public void addAnimation(String animationName, Animation animation)
    {
        animations.put(animationName, animation);
    }

    public void playAnimation(String animationName)
    {
        Animation animation = animations.get(animationName);
        if (!animation.isPlaying())
            animation.play();
    }

    //  We will always reset the animation on stop
    public void stopAnimation(String animationName)
    {
        Animation animation = animations.get(animationName);
        if (animation.isPlaying())
            animation.stopAndReset();
    }
}
