package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.AssaultTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;

import java.util.LinkedList;

/**
 * Created by anand.verma on 10/03/18.
 */

public class AssaultDragonPrefab extends ComponentPrefab
{
	public AssaultDragonPrefab()
	{
		/*
		transform = new Transform(
				new Position(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2 + scaleY(100)));

		StateMachine animationStateMachine =
				StateMachine.builder()
						.startState(FLY_RIGHT)
						.fromAnyStateOnEvent(TURN_RIGHT).toState(FLY_RIGHT)
						.fromAnyStateOnEvent(TURN_LEFT).toState(FLY_LEFT)
						.fromAnyStateOnEvent(INVOKE_ASSUALT_RIGHT).toState
						(FLY_IN_INVOKED_ASSAULT_RIGHT)
						.fromAnyStateOnEvent(INVOKE_ASSUALT_LEFT).toState
						(FLY_IN_INVOKED_ASSAULT_LEFT)
						.fromAnyStateOnEvent(ASSAULT_RIGHT).toState(FLY_TO_ASSAULT_RIGHT)
						.fromAnyStateOnEvent(ASSAULT_LEFT).toState(FLY_TO_ASSAULT_LEFT)
						.build();

		Bitmap dragonSprite1 = Bitmaps.DRAGON_FLY1;
		Bitmap dragonSprite2 = Bitmaps.DRAGON_FLY2;
		Bitmap dragonSprite3 = Bitmaps.DRAGON_FLY3;
		Bitmap dragonSprite4 = Bitmaps.DRAGON_FLY4;
		Bitmap dragonAssault1 = Bitmaps.DRAGON_ASSAULT1;
		Bitmap dragonAssault2 = Bitmaps.DRAGON_ASSAULT2;
		Bitmap dragonAssault3 = Bitmaps.DRAGON_ASSAULT3;
		Bitmap dragonAssault4 = Bitmaps.DRAGON_ASSAULT4;

		Animation idleAnim = new Animation(new Bitmap[]{dragonSprite1}, 0.5f);

		Animation flyRightAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

		Animation assaultRightAnim = new Animation(
				new Bitmap[]{dragonAssault1, dragonAssault2, dragonAssault3, dragonAssault4},
				0.5f);

		Animation assaultInvokeRightAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonAssault2, dragonSprite3, dragonAssault4,
						dragonAssault1, dragonSprite2, dragonAssault3, dragonSprite4},
				1.0f);

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

		dragonAssault1 = Bitmap.createBitmap(dragonAssault1, 0, 0, dragonAssault1.getWidth(),
											 dragonAssault1.getHeight(), m, false);
		dragonAssault2 = Bitmap.createBitmap(dragonAssault2, 0, 0, dragonAssault2.getWidth(),
											 dragonAssault2.getHeight(), m, false);
		dragonAssault3 = Bitmap.createBitmap(dragonAssault3, 0, 0, dragonAssault3.getWidth(),
											 dragonAssault3.getHeight(), m, false);
		dragonAssault4 = Bitmap.createBitmap(dragonAssault4, 0, 0, dragonAssault4.getWidth(),
											 dragonAssault4.getHeight(), m, false);

		Animation flyLeftAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

		Animation assaultLeftAnim = new Animation(
				new Bitmap[]{dragonAssault1, dragonAssault2, dragonAssault3, dragonAssault4},
				0.5f);

		Animation assaultInvokeLeftAnim = new Animation(
				new Bitmap[]{dragonSprite1, dragonAssault2, dragonSprite3, dragonAssault4,
						dragonAssault1, dragonSprite2, dragonAssault3, dragonSprite4},
				1.0f);

		Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, idleAnim);
		stateAnimationMap.put(FLY_RIGHT, flyRightAnim);
		stateAnimationMap.put(FLY_LEFT, flyLeftAnim);
		stateAnimationMap.put(FLY_TO_ASSAULT_RIGHT, assaultRightAnim);
		stateAnimationMap.put(FLY_TO_ASSAULT_LEFT, assaultLeftAnim);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_RIGHT, assaultInvokeRightAnim);
		stateAnimationMap.put(FLY_IN_INVOKED_ASSAULT_LEFT, assaultInvokeLeftAnim);

<<<<<<< HEAD
		BitmapAnimator animator = new BitmapAnimator(stateAnimationMap, animationStateMachine);
=======
		Animator animator = new Animator(stateAnimationMap, animationStateMachine);
<<<<<<< HEAD
		//        animator.triggerTransition(TURN_RIGHT);
		addComponent(new Renderable(dragonSprite1, Vector2D.ZERO, dragonSprite1.getWidth(),
=======
>>>>>>> master

		addComponent(new Renderable(dragonSprite1, Delta.ZERO, dragonSprite1.getWidth(),
>>>>>>> master
									dragonSprite1.getHeight()));
		addComponent(animator);
		*/
		//        animator.triggerTransition(TURN_RIGHT);


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

		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);


		addComponent(new Dragon(EnemyType.DRAXUS));
		addComponent(new RectCollider(Vector2D.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.SUPER_ENEMY));
		addComponent(new AssaultTranslation(PlayerComponent.class, 150, 1500, 5.0f,
				Device.SCREEN_HEIGHT / 4));
		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PhysicsComponent(false));
	}

}
