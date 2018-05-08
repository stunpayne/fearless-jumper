package com.stunapps.fearlessjumper.animation;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.visual.Shape;

import java.util.List;

/**
 * Created by anand.verma on 26/04/18 11:47 PM.
 */

public class ShapeAnimation
{
	protected List<List<Shape>> frames;
	protected boolean playing;

	private int currentFrame;
	private float frameTime;
	private long lastFramePlayTime;

	public ShapeAnimation(List<List<Shape>> frames)
	{
		this(frames, 0.0f);
	}

	public ShapeAnimation(List<List<Shape>> frames, float animTime)
	{
		this.frames = frames;
		this.playing = false;
		this.currentFrame = 0;
		frameTime = animTime / frames.size();
	}

	public boolean isPlaying()
	{
		return playing;
	}

	public List<Shape> play()
	{
		if (System.currentTimeMillis() - lastFramePlayTime > frameTime * 1000)
		{
			currentFrame++;
			currentFrame = currentFrame >= frames.size() ? 0 : currentFrame;
			lastFramePlayTime = System.currentTimeMillis();
		}
		return frames.get(currentFrame);
	}

	public List<Shape> getRenderable()
	{
		return frames.get(currentFrame);
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
