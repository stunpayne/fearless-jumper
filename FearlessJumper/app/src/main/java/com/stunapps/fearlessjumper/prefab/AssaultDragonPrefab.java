package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.ShapeAnimation;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.PathShape;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeAnimator;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.AssaultTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.game.Environment.Device;
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
import static com.stunapps.fearlessjumper.animation.AnimationTransition.INVOKE_ASSUALT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.ASSAULT_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.ASSAULT_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.INVOKE_ASSUALT_RIGHT;

/**
 * Created by anand.verma on 10/03/18.
 */

public class AssaultDragonPrefab extends ComponentPrefab
{
	public AssaultDragonPrefab()
	{
		float radius = 30.0f;
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
				new ShapeAnimation(allShapes, happyAnimationFrameIndices, 2.0f);

		List<List<Integer>> angryAnimationFrameIndices = new LinkedList<>();
		angryAnimationFrameIndices.add(Arrays.asList(0, 1, 2, 3));
		angryAnimationFrameIndices.add(Arrays.asList(4, 5, 6, 7));
		ShapeAnimation angryAnimation =
				new ShapeAnimation(allShapes, angryAnimationFrameIndices, 2.0f);


		List<List<Integer>> assaultAnimationFrameIndices = new LinkedList<>();
		angryAnimationFrameIndices.add(Arrays.asList(8, 9, 10, 11));
		ShapeAnimation assaultAnimation =
				new ShapeAnimation(allShapes, assaultAnimationFrameIndices, 2.0f);

		Map<AnimationState, ShapeAnimation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, angryAnimation);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_LEFT, angryAnimation);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_RIGHT, angryAnimation);
		stateAnimationMap.put(FLY_TO_ASSAULT_RIGHT, assaultAnimation);
		stateAnimationMap.put(FLY_TO_ASSAULT_LEFT, assaultAnimation);

		StateMachine animationStateMachine = StateMachine.builder()
				.startState(IDLE)
				.fromAnyStateOnEvent(INVOKE_ASSUALT_RIGHT).toState(FLY_IN_INVOKED_ASSAULT_RIGHT)
				.fromAnyStateOnEvent(INVOKE_ASSUALT_LEFT).toState(FLY_IN_INVOKED_ASSAULT_LEFT)
				.fromAnyStateOnEvent(ASSAULT_RIGHT).toState(FLY_TO_ASSAULT_RIGHT)
				.fromAnyStateOnEvent(ASSAULT_LEFT).toState(FLY_TO_ASSAULT_LEFT)
				.build();

		ShapeAnimator shapeAnimator = new ShapeAnimator(stateAnimationMap, animationStateMachine);
		addComponent(shapeAnimator);

		ShapeRenderable shapeRenderable = new ShapeRenderable(happyShape, new Vector2D());
		addComponent(shapeRenderable);
		addComponent(new Dragon(EnemyType.DRAXUS));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.SUPER_ENEMY));
		addComponent(new AssaultTranslation(PlayerComponent.class, 150, 1500, 5.0f,
				Device.SCREEN_HEIGHT / 4));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(false));
	}

	@NonNull
	private LinkedList<Shape> getOldShapes()
	{
		LinkedList<Shape> shapes = new LinkedList<>();

		PaintProperties paintProperties = new PaintProperties(null, Color.RED, null, null);
		CircleShape circleShapeLeft = new CircleShape(15, paintProperties, new Vector2D(0, 0));
		shapes.add(circleShapeLeft);

		CircleShape circleShapeRight = new CircleShape(15, paintProperties, new Vector2D(30, 0));
		shapes.add(circleShapeRight);

		CircleShape circleShapeTop = new CircleShape(15, paintProperties, new Vector2D(0, 0));
		shapes.add(circleShapeTop);

		CircleShape circleShapeBottom = new CircleShape(15, paintProperties, new Vector2D(30, 0));
		shapes.add(circleShapeBottom);

		CircleShape circleShapeCenter =
				new CircleShape(15, new PaintProperties(null, Color.BLACK, null, null),
								new Vector2D(15, 15));
		shapes.add(circleShapeCenter);
		return shapes;
	}

	@NonNull
	private List<Shape> getHappyShapes(float radius, Vector2D delta)
	{
		List<Shape> assualtShape = new LinkedList<>();

		CircleShape face =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null), delta);

		Vector2D leftEyeDelta = new Vector2D(delta.getX() + radius / 2, delta.getY() + radius / 2);
		CircleShape leftEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.WHITE, null, null),
								leftEyeDelta);

		Vector2D rightEyeDelta =
				new Vector2D(delta.getX() + radius + radius / 2, delta.getY() + radius/2);
		CircleShape rightEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.WHITE, null, null),
								rightEyeDelta);

		Vector2D moveTo = new Vector2D(delta.getX() + radius / 2, delta.getY() +
				(radius * 4) / 3);
		Vector2D quadTo1 = new Vector2D(radius / 2, (radius * 2) / 3);
		Vector2D quadTo2 = new Vector2D(radius, 0);
		PathShape lips = new PathShape(moveTo, quadTo1, quadTo2,
									   new PaintProperties(null, Color.WHITE, radius / 6,
														   Paint.Style.STROKE), new Vector2D());

		assualtShape.add(face);
		assualtShape.add(leftEye);
		assualtShape.add(rightEye);
		assualtShape.add(lips);
		return assualtShape;
	}

	private List<Shape> getAngryShapes(float radius, Vector2D delta)
	{
		List<Shape> assualtShape = new LinkedList<>();

		CircleShape face =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null), delta);

		Vector2D leftEyeDelta = new Vector2D(delta.getX() + radius / 2, delta.getY() + radius / 2);
		CircleShape leftEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.RED, null, null),
								leftEyeDelta);

		Vector2D rightEyeDelta =
				new Vector2D(delta.getX() + radius + radius / 2, delta.getY() + radius/2);
		CircleShape rightEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.RED, null, null),
								rightEyeDelta);

		Vector2D moveTo = new Vector2D(delta.getX() + radius / 2, delta.getY() +
				(radius * 4) / 3);
		Vector2D quadTo1 = new Vector2D(radius / 2, (radius * 2) / 3);
		Vector2D quadTo2 = new Vector2D(radius, 0);
		PathShape lips = new PathShape(moveTo, quadTo1, quadTo2,
									   new PaintProperties(null, Color.WHITE, radius / 6,
														   Paint.Style.STROKE), new Vector2D());

		assualtShape.add(face);
		assualtShape.add(leftEye);
		assualtShape.add(rightEye);
		assualtShape.add(lips);
		return assualtShape;
	}

	private List<Shape> getAssaultShapes(float radius, Vector2D delta)
	{
		List<Shape> assualtShape = new LinkedList<>();

		CircleShape face =
				new CircleShape(radius, new PaintProperties(null, Color.BLACK, null, null), delta);

		Vector2D leftEyeDelta = new Vector2D(delta.getX() + radius / 2, delta.getY() + radius / 2);
		CircleShape leftEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.RED, null, null),
								leftEyeDelta);

		Vector2D rightEyeDelta =
				new Vector2D(delta.getX() + radius + radius / 2, delta.getY() + radius/2);
		CircleShape rightEye =
				new CircleShape(radius/6, new PaintProperties(null, Color.RED, null, null),
								rightEyeDelta);

		Vector2D moveTo = new Vector2D(delta.getX() + radius / 2, delta.getY() +
				(radius * 4) / 3);
		Vector2D quadTo1 = new Vector2D(radius / 2, 0);
		Vector2D quadTo2 = new Vector2D(radius, 0);
		PathShape lips = new PathShape(moveTo, quadTo1, quadTo2,
									   new PaintProperties(null, Color.RED, radius / 6,
														   Paint.Style.STROKE), new Vector2D());

		assualtShape.add(face);
		assualtShape.add(leftEye);
		assualtShape.add(rightEye);
		assualtShape.add(lips);
		return assualtShape;
	}

}
