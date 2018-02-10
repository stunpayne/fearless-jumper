package com.stunapps.fearlessjumper.animation;

import android.graphics.Bitmap;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Animation
{
    protected Bitmap[] frames;
    protected boolean playing;

    private int currentFrame;
    private float frameTime;
    private long lastFramePlayTime;

    public Animation(Bitmap[] frames)
    {
        this(frames, 0.0f);
    }

    public Animation(Bitmap[] frames, float animTime)
    {
        this.frames = frames;
        this.playing = false;
        this.currentFrame = 0;
        frameTime = animTime / frames.length;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public Bitmap play()
    {
        if (System.currentTimeMillis() - lastFramePlayTime > frameTime * 1000)
        {
            currentFrame++;
            currentFrame = currentFrame >= frames.length ? 0 : currentFrame;
            lastFramePlayTime = System.currentTimeMillis();
        }
        return frames[currentFrame];
    }

    public Bitmap getRenderable()
    {
        return frames[currentFrame];
    }

    public void stop()
    {
        playing = false;
    }

    public void reset()
    {
        currentFrame = 0;
    }

    public void stopAndReset()
    {
        stop();
        reset();
    }
}
