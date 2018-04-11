package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.animation.BaseShapeAnimation;
import com.stunapps.fearlessjumper.animation.ShapeColorAnimation;
import com.stunapps.fearlessjumper.animation.ShapeSizeAnimation;
import com.stunapps.fearlessjumper.component.Component;

import java.util.Map;

/**
 * Created by sunny.s on 11/04/18.
 */

public class ShapeAnimator extends Component
{
	private Map<AnimationType, ? extends BaseShapeAnimation> animationMap;

	public ShapeAnimator(Map<AnimationType, ? extends BaseShapeAnimation> animationMap)
	{
		super(ShapeAnimator.class);

		for (AnimationType type : animationMap.keySet())
		{
			validate(type, animationMap.get(type));
		}

		this.animationMap = animationMap;
	}

	public enum AnimationType
	{
		COLOR(ShapeColorAnimation.class), SIZE(ShapeSizeAnimation.class);

		private Class allowedShapeAnimation;

		public Class getAllowedShapeAnimation()
		{
			return allowedShapeAnimation;
		}

		AnimationType(Class allowedShapeAnimation)
		{
			this.allowedShapeAnimation = allowedShapeAnimation;
		}
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return null;
	}

	private void validate(AnimationType animationType, BaseShapeAnimation animation)
	{
		if (!animationType.getAllowedShapeAnimation().equals(animation.getClass()))
			throw new IllegalArgumentException(
					"Incorrect animation type: " + animation.getClass() + " for type: " +
							animationType.getAllowedShapeAnimation());
	}
}
