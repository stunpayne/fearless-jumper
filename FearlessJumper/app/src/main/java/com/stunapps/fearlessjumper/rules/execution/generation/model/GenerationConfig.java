package com.stunapps.fearlessjumper.rules.execution.generation.model;

import com.stunapps.fearlessjumper.rules.execution.generation.enums.GenerationLocation;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationConfig
{
	private int maxInARow;
	private int blockFrameCount;
	private Float offsetY;
	private Float maxMarginX;
	private GenerationLocation generationLocation;

	public GenerationConfig(int maxInARow, int blockFrameCount, Float maxMarginX, Float offsetY,
			GenerationLocation generationLocation)
	{
		this.maxInARow = maxInARow;
		this.blockFrameCount = blockFrameCount;
		this.maxMarginX = maxMarginX;
		this.offsetY = offsetY;
		this.generationLocation = generationLocation;
	}

	public int getMaxInARow()
	{
		return maxInARow;
	}

	public int getBlockFrameCount()
	{
		return blockFrameCount;
	}

	public Float getOffsetY()
	{
		return offsetY;
	}

	public GenerationLocation getGenerationLocation()
	{
		return generationLocation;
	}

	public Float getMaxMarginX()
	{
		return maxMarginX;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private int maxInARow = 1;
		private int blockFrameCount = 1;
		private Float offsetY = 600f;
		private Float maxMarginX;
		private GenerationLocation generationLocation = GenerationLocation.X_ANYWHERE;

		public Builder maxInARow(int maxInARow)
		{
			this.maxInARow = maxInARow;
			return this;
		}

		public Builder blockFrameCount(int blockFrameCount)
		{
			this.blockFrameCount = blockFrameCount;
			return this;
		}

		public Builder generationOffsetY(Float offsetY)
		{
			this.offsetY = offsetY;
			return this;
		}

		public Builder maxMarginX(Float maxMarginX)
		{
			this.maxMarginX = maxMarginX;
			return this;
		}

		public Builder generationLocation(GenerationLocation generationLocation)
		{
			this.generationLocation = generationLocation;
			return this;
		}

		public GenerationConfig build()
		{
			return new GenerationConfig(maxInARow, blockFrameCount, maxMarginX, offsetY,
					generationLocation);
		}
	}
}
