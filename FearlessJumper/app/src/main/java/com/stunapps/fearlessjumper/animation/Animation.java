package com.stunapps.fearlessjumper.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Animation
{
    protected Bitmap[] frames;
    protected boolean playing;

    private int currentFrame;
    private float frameTime;
    private long lastFrame;

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
        if (System.currentTimeMillis() - lastFrame > frameTime * 1000)
        {
            currentFrame++;
            currentFrame = currentFrame >= frames.length ? 0 : currentFrame;
            lastFrame = System.currentTimeMillis();
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
