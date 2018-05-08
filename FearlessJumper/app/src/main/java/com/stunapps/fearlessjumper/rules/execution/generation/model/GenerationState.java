package com.stunapps.fearlessjumper.rules.execution.generation.model;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationState
{
	private boolean blocked;
	private int currInARow;

	public GenerationState()
	{
		this.currInARow = 0;
	}

	public void updateFrom(GenerationState other)
	{
		if (other == null)
		{
			return;
		}
		if (blocked != other.isBlocked())
		{
			blocked = other.isBlocked();
		}
	}

	public int getCurrInARow()
	{
		return currInARow;
	}

	public boolean isBlocked()
	{
		return blocked;
	}

	public void incrementCurrInARow()
	{
		currInARow += 1;
	}

	public void resetCurrInARow()
	{
		currInARow = 0;
	}

	public void block()
	{
		this.blocked = true;
	}

	public void unblock()
	{
		this.blocked = false;
	}
}
