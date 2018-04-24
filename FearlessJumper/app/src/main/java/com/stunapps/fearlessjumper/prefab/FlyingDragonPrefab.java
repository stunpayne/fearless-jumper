package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;
import android.graphics.Paint.Style;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.model.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;

/**
 * Created by sunny.s on 21/01/18.
 */

public class FlyingDragonPrefab extends ComponentPrefab
{
	public FlyingDragonPrefab()
	{
		/*
		transform = new Transform(
				new Position(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2 + 100));

		StateMachine<AnimationState, AnimationTransition> animationStateMachine =
				StateMachine.builder().startState(FLY_RIGHT)
						.fromAnyStateOnEvent(TURN_RIGHT).toState(FLY_RIGHT)
						.fromAnyStateOnEvent(TURN_LEFT).toState(FLY_LEFT)
						.build();

		Bitmap dragonSprite1 = Bitmaps.DRAGON_FLY1;
		Bitmap dragonSprite2 = Bitmaps.DRAGON_FLY2;
		Bitmap dragonSprite3 = Bitmaps.DRAGON_FLY3;
		Bitmap dragonSprite4 = Bitmaps.DRAGON_FLY4;

		Animation idleAnim = new Animation(new Bitmap[]{dragonSprite1}, 0.5f);

		Animation flyRightAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

		Matrix m = new Matrix();
		m.preScale(-1, 1);
		dragonSprite1 = Bitmap.createBitmap(dragonSprite1, 0, 0, dragonSprite1.getWidth(),
				dragonSprite1.getHeight(), m, false);
		dragonSprite2 = Bitmap.createBitmap(dragonSprite2, 0, 0, dragonSprite2.getWidth(),
				dragonSprite2.getHeight(), m, false);
		dragonSprite3 = Bitmap.createBitmap(dragonSprite3, 0, 0, dragonSprite3.getWidth(),
				dragonSprite3.getHeight(), m, false);
		dragonSprite4 = Bitmap.createBitmap(dragonSprite4, 0, 0, dragonSprite4.getWidth(),
				dragonSprite4.getHeight(), m, false);

		Animation flyLeftAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

		Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, idleAnim);
		stateAnimationMap.put(FLY_RIGHT, flyRightAnim);
		stateAnimationMap.put(FLY_LEFT, flyLeftAnim);

		Animator animator = new Animator(stateAnimationMap, animationStateMachine);
		//        animator.triggerTransition(TURN_RIGHT);
		addComponent(new Renderable(dragonSprite1, Delta.ZERO, dragonSprite1.getWidth(),
				dragonSprite1.getHeight()));
		addComponent(animator);
		*/

		/*LinkedList<Shape> shapes = new LinkedList<>();

		CircleShape circleShapeLeft = new CircleShape(15, new PaintProperties(null, Color.RED),
												  new Vector2D());
		shapes.add(circleShapeLeft);

		CircleShape circleShapeRight = new CircleShape(15, new PaintProperties(null, Color.RED),
												  new Vector2D(30,0));
		shapes.add(circleShapeRight);

		RectShape rectShape = new RectShape(30, 30, new PaintProperties(null, Color.BLACK),
											new Vector2D(15,0));
		shapes.add(rectShape);*/


		LinkedList<Shape> shapes = new LinkedList<>();

		CircleShape circleShape =
				new CircleShape(15, new PaintProperties(null, Color.BLACK, null, null), new
						Vector2D(15,
																						   15));
		shapes.add(circleShape);
		PaintProperties linePaintProperties = new PaintProperties(null, Color.GRAY, 8.0f, Style
				.STROKE);
		LineShape lineShapeLeft =
				new LineShape(0, circleShape.getTop() + circleShape.getRadius(), 15,
							  circleShape.getTop() + circleShape.getRadius(), linePaintProperties);
		shapes.add(lineShapeLeft);

		LineShape lineShapeRight = new LineShape(circleShape.getRight(),
												 circleShape.getTop() + circleShape.getRadius(),
												 circleShape.getRight() + 15,
												 circleShape.getTop() + circleShape.getRadius(),
												 linePaintProperties);
		shapes.add(lineShapeRight);

		LineShape lineShapeTop = new LineShape(circleShape.getLeft() + circleShape.getRadius(), 0,
											   circleShape.getLeft() + circleShape.getRadius(),
											   circleShape.getTop(),
											   linePaintProperties);
		shapes.add(lineShapeTop);

		LineShape lineShapeBottom = new LineShape(circleShape.getLeft() + circleShape.getRadius(),
												  circleShape.getBottom(),
												  circleShape.getLeft() + circleShape.getRadius(),
												  circleShape.getBottom() + 15,
												  linePaintProperties);
		shapes.add(lineShapeBottom);

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new Dragon(EnemyType.MINIGON));
		addComponent(
				new RectCollider(Delta.ZERO, shapeRenderable.getWidth(), shapeRenderable.getHeight(),
						CollisionLayer.ENEMY));
		addComponent(new PeriodicTranslation()
				.withAbsoluteXMovement(0, Device.SCREEN_WIDTH - shapeRenderable.getWidth(),
						250f));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, 20.0f, false));
	}
}