package com.stunapps.fearlessjumper.prefab;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.visual.CircleShape;
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
import com.stunapps.fearlessjumper.component.specific.PeriodicGun;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;

import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 10/03/18.
 */

public class ShooterDragonPrefab extends ComponentPrefab
{
	public ShooterDragonPrefab()
	{
		/*
		StateMachine animationStateMachine =
				StateMachine.builder().startState(FLY_RIGHT).from(IDLE).onEvent(TURN_LEFT)
						.toState(FLY_LEFT).from(IDLE).onEvent(TURN_RIGHT).toState(FLY_RIGHT)
						.from(FLY_LEFT).onEvent(TURN_RIGHT).toState(FLY_RIGHT).from(FLY_RIGHT)
						.onEvent(TURN_LEFT).toState(FLY_LEFT).build();

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
		addComponent(new Renderable(dragonSprite1, Delta.ZERO, dragonSprite1.getWidth(),
				dragonSprite1.getHeight()));

		addComponent(animator);
		*/

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(
				new CircleShape(60, new PaintProperties(null, Color.rgb(25, 25, 112), null, null),
						new Vector2D()));
		ShapeRenderable shapeRenderable = new ShapeRenderable(shapes, new Vector2D());
		addComponent(shapeRenderable);

		addComponent(new Dragon(EnemyType.GRORUM));
		addComponent(new RectCollider(Delta.ZERO, shapeRenderable.getWidth(),
				shapeRenderable.getHeight(), CollisionLayer.ENEMY));
		addComponent(new PeriodicTranslation().withAnchoredYMovement(100f, scaleY(100f)));
		addComponent(new PhysicsComponent(Float.MAX_VALUE, Velocity.ZERO, false));

		addComponent(new ContactDamageComponent(1, false));
		addComponent(new PeriodicGun(2000));
	}
}
