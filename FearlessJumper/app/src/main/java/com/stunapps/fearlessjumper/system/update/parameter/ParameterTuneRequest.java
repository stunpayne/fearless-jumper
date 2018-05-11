package com.stunapps.fearlessjumper.system.update.parameter;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.ComponentType;
import com.stunapps.fearlessjumper.entity.Entity;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */
class ParameterTuneRequest
{
	ComponentManager componentManager;
	Entity entity;
	ComponentType componentType;
	String field;
	String threshold;
	String updateValue;

	public ParameterTuneRequest(ComponentManager componentManager, Entity entity,
			ComponentType componentType, String field, String threshold, String value)
	{
		this.componentManager = componentManager;
		this.entity = entity;
		this.componentType = componentType;
		this.field = field;
		this.threshold = threshold;
		this.updateValue = value;
	}

	public ComponentManager getComponentManager()
	{
		return componentManager;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public ComponentType getComponentType()
	{
		return componentType;
	}

	public String getField()
	{
		return field;
	}

	public String getThreshold()
	{
		return threshold;
	}

	public String getValue()
	{
		return updateValue;
	}

	public class ScoreBasedTuneRequest extends ParameterTuneRequest
	{

		public ScoreBasedTuneRequest(ComponentManager componentManager, Entity entity, String
				field,
				String threshold, String value)
		{
			super(componentManager, entity, ComponentType.SCORE, field, threshold, value);
		}
	}
}
