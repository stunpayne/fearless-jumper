package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.HashMap;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationState.HURT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationState.TERMINATED;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.NORMALIZE;
import static com.stunapps.fearlessjumper.animation.AnimationTransition.TERMINATE;

/**
 * Created by anand.verma on 12/01/18.
 */

public class PlayerPrefab extends ComponentPrefab
{
	public PlayerPrefab()
	{
		Bitmap alien = Bitmaps.PLAYER_IDLE;
		Bitmap alienHurt = Bitmaps.PLAYER_HURT;
		Bitmap blankImage = Bitmaps.BLANK_IMAGE;
		Bitmap alienDied = Bitmaps.PLAYER_DIED;

        Animation alienAnim = new Animation(new Bitmap[]{alien}, 0.5f);
        Animation alienHurtAnim = new Animation(new Bitmap[]{blankImage, alienHurt, blankImage}, 0.01f);
        Animation alienDiedAnim = new Animation(new Bitmap[]{alienDied}, 0.5f);

        Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
        stateAnimationMap.put(IDLE, alienAnim);
        stateAnimationMap.put(HURT, alienHurtAnim);
        stateAnimationMap.put(TERMINATED, alienDiedAnim);

		StateMachine animationStateMachine =
				StateMachine.builder()
						.startState(IDLE)
						.from(IDLE).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onCountDown(1000l).toState(IDLE)
						.fromAnyStateOnEvent(TERMINATE).toState(TERMINATED)
						.fromAnyStateOnEvent(NORMALIZE).toState(IDLE)
						.terminalState(TERMINATED)
						.build();

		addComponent(new Renderable(alien, Delta.ZERO, alien.getWidth(),
				alien.getHeight()));
		addComponent(new Animator(stateAnimationMap, animationStateMachine));

		addComponent(new RectCollider(Delta.ZERO, alien.getWidth(), alien.getHeight(),
										CollisionLayer.PLAYER));

		addComponent(new Health(1));
		addComponent(new PhysicsComponent(50, new Velocity()));
		OrientationInput orientationInput = DI.di().getInstance(OrientationInput.class);
		addComponent(orientationInput);
		addComponent(new PlayerComponent());
		addComponent(new RemainingTime(-97000));
		addComponent(new Score());
		addComponent(new Fuel(100f));
	}
}
