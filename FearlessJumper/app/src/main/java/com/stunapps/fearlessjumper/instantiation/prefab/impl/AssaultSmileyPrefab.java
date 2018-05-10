package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import android.graphics.Color;
import android.graphics.Paint.Style;
import android.support.annotation.NonNull;

import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.ShapeAnimation;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.AssaultTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.PathShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeAnimator;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.UXParameters;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_IN_INVOKED_ASSAULT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_IN_INVOKED_ASSAULT_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_TO_ASSAULT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_TO_ASSAULT_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.ASSAULT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.ASSAULT_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.INVOKE_ASSUALT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.INVOKE_ASSUALT_RIGHT;
import static com.stunapps.fearlessjumper.game.Environment.unitX;
import static com.stunapps.fearlessjumper.game.UXParameters.ASSAULT_SMILEY_ATTACK_SPEED;
import static com.stunapps.fearlessjumper.game.UXParameters.ASSAULT_SMILEY_WAIT_TIME;

/**
 * Created by anand.verma on 10/03/18.
 */

public class AssaultSmileyPrefab extends ComponentPrefab
{
	private static final PaintProperties BLACK_PAINT =
			new PaintProperties(null, Color.BLACK, null, null);
	private static final PaintProperties WHITE_PAINT =
			new PaintProperties(null, Color.WHITE, null, null);

	private static final PaintProperties RED_PAINT =
			new PaintProperties(null, Color.RED, null, null);

	public AssaultSmileyPrefab()
	{
		float radius = unitX() * 1.2f;
		Vector2D delta = new Vector2D();
		List<Shape> happyShape = getHappyShapes(radius, delta);
		List<Shape> angryShape = getAngryShapes(radius, delta);
		List<Shape> assaultShape = getAssaultShapes(radius, delta);

		List<Shape> allShapes = new LinkedList<>();
		allShapes.addAll(happyShape);
		allShapes.addAll(angryShape);
		allShapes.addAll(assaultShape);

		List<List<Integer>> happyAnimationFrameIndices = new LinkedList<>();
		happyAnimationFrameIndices.add(Arrays.asList(0, 1, 2, 3));
		ShapeAnimation idleAnimation =
				new ShapeAnimation(allShapes, happyAnimationFrameIndices, 0.0f);

		List<List<Integer>> angryAnimationFrameIndices = new LinkedList<>();
		angryAnimationFrameIndices.add(Arrays.asList(0, 1, 2, 3));
		angryAnimationFrameIndices.add(Arrays.asList(4, 5, 6, 7));
		ShapeAnimation angryAnimation =
				new ShapeAnimation(allShapes, angryAnimationFrameIndices, 0.3f);

		List<List<Integer>> assaultAnimationFrameIndices = new LinkedList<>();
		assaultAnimationFrameIndices.add(Arrays.asList(8, 9, 10, 11));
		ShapeAnimation assaultAnimation =
				new ShapeAnimation(allShapes, assaultAnimationFrameIndices, 0.0f);

		Map<AnimationState, ShapeAnimation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, idleAnimation);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_LEFT, angryAnimation);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_RIGHT, angryAnimation);
		stateAnimationMap.put(FLY_TO_ASSAULT_RIGHT, assaultAnimation);
		stateAnimationMap.put(FLY_TO_ASSAULT_LEFT, assaultAnimation);

		StateMachine animationStateMachine =
				StateMachine.builder().startState(IDLE).fromAnyStateOnEvent(INVOKE_ASSUALT_RIGHT)
						.toState(FLY_IN_INVOKED_ASSAULT_RIGHT)
						.fromAnyStateOnEvent(INVOKE_ASSUALT_LEFT)
						.toState(FLY_IN_INVOKED_ASSAULT_LEFT).fromAnyStateOnEvent(ASSAULT_RIGHT)
						.toState(FLY_TO_ASSAULT_RIGHT).fromAnyStateOnEvent(ASSAULT_LEFT)
						.toState(FLY_TO_ASSAULT_LEFT).build();

		ShapeRenderable shapeRenderable = new ShapeRenderable(happyShape, new Vector2D());
		ShapeAnimator shapeAnimator = new ShapeAnimator(stateAnimationMap, animationStateMachine);

		addComponent(shapeRenderable);
		addComponent(shapeAnimator);
		addComponent(new Dragon(EnemyType.DRAXUS));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.SUPER_ENEMY));
		addComponent(new AssaultTranslation(PlayerComponent.class, ASSAULT_SMILEY_ATTACK_SPEED,
				ASSAULT_SMILEY_WAIT_TIME, 5.0f, Device.SCREEN_HEIGHT / 4));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(false));
	}

	@NonNull
	private List<Shape> getHappyShapes(float radius, Vector2D delta)
	{
		List<Shape> happyShape = new LinkedList<>();

		CircleShape face = getFace(radius, delta, BLACK_PAINT);

		CircleShape leftEye = getLeftEye(radius, delta, WHITE_PAINT);

		CircleShape rightEye = getRightEye(radius, delta, WHITE_PAINT);

		PathShape lips = getLips(radius, delta, getWhitePaintWithStroke(radius / 6, Style.STROKE));

		happyShape.add(face);
		happyShape.add(leftEye);
		happyShape.add(rightEye);
		happyShape.add(lips);
		return happyShape;
	}

	private List<Shape> getAngryShapes(float radius, Vector2D delta)
	{
		List<Shape> angryShape = new LinkedList<>();

		CircleShape face = getFace(radius, delta, BLACK_PAINT);

		CircleShape leftEye = getLeftEye(radius, delta, RED_PAINT);

		CircleShape rightEye = getRightEye(radius, delta, RED_PAINT);

		PathShape lips = getLips(radius, delta, getWhitePaintWithStroke(radius / 6, Style.STROKE));

		angryShape.add(face);
		angryShape.add(leftEye);
		angryShape.add(rightEye);
		angryShape.add(lips);
		return angryShape;
	}

	private List<Shape> getAssaultShapes(float radius, Vector2D delta)
	{
		List<Shape> assaultShape = new LinkedList<>();

		CircleShape face = getFace(radius, delta, BLACK_PAINT);

		CircleShape leftEye = getLeftEye(radius, delta, RED_PAINT);

		CircleShape rightEye = getRightEye(radius, delta, RED_PAINT);

		PathShape lips = getLips(radius, delta, getRedPaintWithStroke(radius / 6, Style.STROKE));

		assaultShape.add(face);
		assaultShape.add(leftEye);
		assaultShape.add(rightEye);
		assaultShape.add(lips);
		return assaultShape;
	}

	private PaintProperties getWhitePaintWithStroke(float strokeWidth, Style style)
	{
		WHITE_PAINT.setStrokeWidth(strokeWidth);
		WHITE_PAINT.setStyle(style);
		return WHITE_PAINT;
	}

	private PaintProperties getRedPaintWithStroke(float strokeWidth, Style style)
	{
		RED_PAINT.setStrokeWidth(strokeWidth);
		RED_PAINT.setStyle(style);
		return RED_PAINT;
	}

	@NonNull
	private PathShape getLips(float radius, Vector2D delta, PaintProperties paintProperties)
	{
		Vector2D moveTo = new Vector2D(delta.getX() + radius / 2, delta.getY() + (radius * 4) / 3);
		Vector2D quadTo1 = new Vector2D(radius / 2, radius / 2);
		Vector2D quadTo2 = new Vector2D(radius, 0);
		return new PathShape(moveTo, quadTo1, quadTo2, paintProperties, new Vector2D());
	}

	@NonNull
	private CircleShape getRightEye(float radius, Vector2D delta, PaintProperties paintProperties)
	{
		Vector2D rightEyeDelta =
				new Vector2D(delta.getX() + radius + radius / 4, delta.getY() + radius / 2);
		return new CircleShape(radius / 6, paintProperties, rightEyeDelta);
	}

	@NonNull
	private CircleShape getLeftEye(float radius, Vector2D delta, PaintProperties paintProperties)
	{
		Vector2D leftEyeDelta = new Vector2D(delta.getX() + radius / 2, delta.getY() + radius / 2);
		return new CircleShape(radius / 6, paintProperties, leftEyeDelta);
	}

	@NonNull
	private CircleShape getFace(float radius, Vector2D delta, PaintProperties paintProperties)
	{
		return new CircleShape(radius, paintProperties, delta);
	}
}
