package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.component.Delta;

import java.util.HashMap;
import java.util.Map;

import lombok.Singular;

/**
 * Created by sunny.s on 03/01/18.
 */

public class AnimatorComponent extends RenderableComponent<Bitmap>
{
    @Singular
    private Map<String, Animation> animations = new HashMap<>();
    private String currentlyPlayingAnimation = null;

    public AnimatorComponent(Map<String, Animation> animations, Delta delta, float width, float height)
    {
        super(RenderType.ANIMATOR, delta, width, height);
        this.animations = animations;
    }

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
        if (null != currentlyPlayingAnimation)
        {
            animations.get(currentlyPlayingAnimation).stop();
            currentlyPlayingAnimation = null;
        }
        Animation animation = animations.get(animationName);
        if (!animation.isPlaying())
            animation.play();
        currentlyPlayingAnimation = animationName;
    }

    //  We will always reset the animation on stop
    public void stopAnimation(String animationName)
    {
        Animation animation = animations.get(animationName);
        if (animation.isPlaying())
            animation.stopAndReset();
        currentlyPlayingAnimation = null;
    }

    @Override
    public Bitmap getRenderable()
    {
        return null;
    }
}
