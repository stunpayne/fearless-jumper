package com.stunapps.fearlessjumper.rules.execution.generation.model;

import com.stunapps.fearlessjumper.rules.execution.generation.enums.GenerationLocation;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationConfig
{
	private int maxInARow;
	private Float weight;
	private Float offsetY;
	private Float maxMarginX;
	private GenerationLocation generationLocation;

	public GenerationConfig(int maxInARow, Float maxMarginX, Float offsetY,
			GenerationLocation generationLocation)
	{
		this.maxInARow = maxInARow;
		this.maxMarginX = maxMarginX;
		this.offsetY = offsetY;
		this.generationLocation = generationLocation;
	}

	public int getMaxInARow()
	{
		return maxInARow;
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
		private int maxInARow = 5;
		private Float weight = 5f;
		private Float offsetY = 600f;
		private Float maxMarginX;
		private GenerationLocation generationLocation = GenerationLocation.X_ANYWHERE;

		public Builder maxInARow(int maxInARow)
		{
			this.maxInARow = maxInARow;
			return this;
		}

		public Builder weight(Float weight)
		{
			this.weight = weight;
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
			return new GenerationConfig(maxInARow, maxMarginX, offsetY, generationLocation);
		}
	}
}
