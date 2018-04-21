package com.stunapps.fearlessjumper.rules.generation.model;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationConfig
{
	private int maxInARow;
	private float weight;
	private float generationOffsetY;
	private GenerationLocation generationLocation;

	public GenerationConfig(int maxInARow, float generationOffsetY,
			GenerationLocation generationLocation)
	{
		this.maxInARow = maxInARow;
		this.generationOffsetY = generationOffsetY;
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

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private int maxInARow = 5;
		private float weight = 5f;
		private float generationOffsetY = 600f;
		private GenerationLocation generationLocation = GenerationLocation.X_ANYWHERE;

		public Builder maxInARow(int maxInARow)
		{
			this.maxInARow = maxInARow;
			return this;
		}

		public Builder weight(float weight)
		{
			this.weight = weight;
			return this;
		}

		public Builder generationOffsetY(float generationOffsetY)
		{
			this.generationOffsetY = generationOffsetY;
			return this;
		}

		public Builder generationLocation(GenerationLocation generationLocation)
		{
			this.generationLocation = generationLocation;
			return this;
		}

		public GenerationConfig build()
		{
			return new GenerationConfig(maxInARow, generationOffsetY, generationLocation);
		}
	}
}
