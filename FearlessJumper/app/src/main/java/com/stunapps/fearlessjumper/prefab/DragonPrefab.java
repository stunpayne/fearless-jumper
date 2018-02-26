package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Dragon;
import com.stunapps.fearlessjumper.component.specific.Obstacle;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

import java.util.HashMap;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationTransition.TURN_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.TURN_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_RIGHT;

/**
 * Created by sunny.s on 21/01/18.
 */

public class DragonPrefab extends Prefab
{
	public DragonPrefab()
	{
		transform = new Transform(new Position(Device.SCREEN_WIDTH / 2, 200));

		StateMachine animationStateMachine =
				StateMachine.builder().startState(FLY_RIGHT).from(IDLE).onEvent(TURN_LEFT)
						.toState(FLY_LEFT).from(IDLE).onEvent(TURN_RIGHT).toState(FLY_RIGHT)
						.from(FLY_LEFT).onEvent(TURN_RIGHT).toState(FLY_RIGHT).from(FLY_RIGHT)
						.onEvent(TURN_LEFT).toState(FLY_LEFT).build();

//		Bitmap dragonSprite1 = BitmapFactory
//				.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dragon_fly1);
//		Bitmap dragonSprite2 = BitmapFactory
//				.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dragon_fly2);
//		Bitmap dragonSprite3 = BitmapFactory
//				.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dragon_fly3);
//		Bitmap dragonSprite4 = BitmapFactory
//				.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dragon_fly4);
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
		//        animator.triggerEvent(TURN_RIGHT);
		components.add(new Renderable(dragonSprite1, Delta.ZERO, dragonSprite1.getWidth(),
				dragonSprite1.getHeight()));
		components.add(animator);
		components.add(new Dragon());
		components.add(new Obstacle());
		components.add(new RectCollider(Delta.ZERO, dragonSprite1.getWidth(),
				dragonSprite1.getHeight()));
		components.add(new PeriodicTranslation()
				.withXMovement(0, Device.SCREEN_WIDTH - dragonSprite1.getWidth(), 5f));
		components.add(new ContactDamageComponent(1));
		components
				.add(new PhysicsComponent(Float.MAX_VALUE, new PhysicsComponent.Velocity(),
						false));
	}
}