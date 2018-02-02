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

    public Animation(Bitmap[] frames)
    {
        this.frames = frames;
        playing = false;
        currentFrame = 0;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public Bitmap play()
    {
        currentFrame++;
        currentFrame %= frames.length;
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

    private void scaleRect(Rect rect)
    {
        float whRatio = (float) (frames[currentFrame].getWidth()) / frames[currentFrame].getHeight();
        if (rect.width() > rect.height())
        {
            rect.left = rect.right - (int) (rect.height() * whRatio);
        } else
        {
            rect.top = rect.bottom - (int) (rect.width() / whRatio);
        }
    }
}
