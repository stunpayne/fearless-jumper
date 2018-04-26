package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;

import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.ShapeAnimation;
import com.stunapps.fearlessjumper.component.visual.ArcShape;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeAnimator;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PeriodicGun;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.TURN_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.TURN_RIGHT;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 10/03/18.
 */

public class ShooterDragonPrefab extends ComponentPrefab
{
	public ShooterDragonPrefab()
	{
		LinkedList<Shape> openMouthShape = new LinkedList<>();

		CircleShape eye = new CircleShape(7, new PaintProperties(null, Color.WHITE, null, null),
										  new Vector2D(70, 15));

		openMouthShape.add(new ArcShape(new Vector2D(), 100, 100, 15, 330, true,
										new PaintProperties(null, Color.rgb(25, 25, 112), null,
															null)));
		openMouthShape.add(eye);


		LinkedList<Shape> midOpenMouthShape = new LinkedList<>();
		midOpenMouthShape.add(new ArcShape(new Vector2D(), 100, 100, 10, 340, true,
										   new PaintProperties(null, Color.rgb(25, 25, 112), null,
															   null)));
		midOpenMouthShape.add(eye);


		LinkedList<Shape> smallOpenMouthShape = new LinkedList<>();
		smallOpenMouthShape.add(new ArcShape(new Vector2D(), 100, 100, 5, 350, true,
											 new PaintProperties(null, Color.rgb(25, 25, 112),
																 null,
																 null)));
		smallOpenMouthShape.add(eye);


		LinkedList<Shape> closedMouthShape = new LinkedList<>();

		closedMouthShape.add(new ArcShape(new Vector2D(), 100, 100, 0, 360, true,
										  new PaintProperties(null, Color.rgb(25, 25, 112), null,
															  null)));
		closedMouthShape.add(eye);


		StateMachine animationStateMachine = StateMachine.builder().startState(IDLE).build();

		List<List<Shape>> mouthMovementShapes = new LinkedList<>();
		mouthMovementShapes.add(openMouthShape);
		mouthMovementShapes.add(midOpenMouthShape);
		mouthMovementShapes.add(smallOpenMouthShape);
		mouthMovementShapes.add(closedMouthShape);
		ShapeAnimation groramAnimation = new ShapeAnimation(mouthMovementShapes, 2.0f);

		Map<AnimationState, ShapeAnimation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, groramAnimation);

		ShapeAnimator shapeAnimator = new ShapeAnimator(stateAnimationMap, animationStateMachine);
		addComponent(shapeAnimator);

		ShapeRenderable shapeRenderable = new ShapeRenderable(openMouthShape, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new Dragon(EnemyType.GRORUM));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
									  shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new PeriodicTranslation().withAnchoredYMovement(100f, scaleY(100f)));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false));

		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PeriodicGun(2000));
	}
}
