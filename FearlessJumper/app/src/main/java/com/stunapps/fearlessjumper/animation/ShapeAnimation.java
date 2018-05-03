package com.stunapps.fearlessjumper.animation;

import com.stunapps.fearlessjumper.component.visual.Shape;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anand.verma on 26/04/18 11:47 PM.
 */

public class ShapeAnimation
{
	protected List<Shape> allShapes;
	protected List<List<Integer>> frames;
	protected List<Shape> frameShapes;
	protected boolean playing;

	private int currentFrame;
	private float frameTime;
	private long lastFramePlayTime;

	public ShapeAnimation(List<Shape> allShapes, List<List<Integer>> frames, float animTime)
	{
		this.allShapes = allShapes;
		this.frameShapes = new LinkedList<>();
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
		return getShapes(frames.get(currentFrame));
	}

	public List<Shape> getRenderable()
	{
		return getShapes(frames.get(currentFrame));
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

	private List<Shape> getShapes(List<Integer> frame)
	{
		frameShapes.clear();
		for (Integer shapeIndex : frame)
		{
			frameShapes.add(allShapes.get(shapeIndex));
		}
		return frameShapes;
	}
}
