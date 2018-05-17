package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 19/02/18.
 */

public class Score extends Component
{
	private float score;

	public Score()
	{
		super(Score.class);
		this.score = 0f;
	}

	public Score(Score other)
	{
		this();
		this.score = other.getScore();
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new Score(this);
	}

	public float getScore()
	{
		return score;
	}

	public void addScore(float amount)
	{
		this.score += amount;
	}
}
