package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;

import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.visual.BitmapAnimator;
import com.stunapps.fearlessjumper.model.Vector2D;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterShape;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;
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
		Animation alienHurtAnim =
				new Animation(new Bitmap[]{blankImage, alienHurt, blankImage}, 0.01f);
		Animation alienDiedAnim = new Animation(new Bitmap[]{alienDied}, 0.5f);

		Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
		stateAnimationMap.put(IDLE, alienAnim);
		stateAnimationMap.put(HURT, alienHurtAnim);
		stateAnimationMap.put(TERMINATED, alienDiedAnim);

		StateMachine animationStateMachine =
				StateMachine.builder().startState(IDLE).from(IDLE).onEvent(AnimationTransition
						.HURT)
						.toState(HURT).from(HURT).onEvent(AnimationTransition.HURT).toState(HURT)
						.from(HURT).onCountDown(1000l).toState(IDLE).fromAnyStateOnEvent(TERMINATE)
						.toState(TERMINATED).fromAnyStateOnEvent(NORMALIZE).toState(IDLE)
						.terminalState(TERMINATED).build();

		addComponent(new Renderable(alien, Vector2D.ZERO, alien.getWidth(), alien.getHeight()));
		addComponent(new BitmapAnimator(stateAnimationMap, animationStateMachine));

		addComponent(new RectCollider(Vector2D.ZERO, alien.getWidth(), alien.getHeight(),
				CollisionLayer.PLAYER));

		addComponent(new Health(100));
		addComponent(new PhysicsComponent(50, Velocity.ZERO, 60));
		addComponent(new Input(true, false));
		addComponent(new PlayerComponent());
		addComponent(new RemainingTime(120000));
		addComponent(new Score());
		addComponent(new Fuel(100f));
		addComponent(new EternalEmitter(
				EmitterConfig.builder().emitterShape(EmitterShape.CONE_DIVERGE)
						.maxParticles(200)
						.particleLife(300)
						.emissionRate(200)
						.positionVar(new Vector2D(13, 0))
						.maxSpeed(3)
						.direction(-90)
						.directionVar(40)
						.offset(new Vector2D(alien.getWidth() / 2, alien.getHeight()))
						.size(20)
						.colorLimits(Color.RED, Color.RED | Color.BLUE)
						.blendingMode(Mode.ADD)
						.build()));
	}
}
