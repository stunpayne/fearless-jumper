package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationEvent;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.AnimatorComponent;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationEvent.TERMINATE;
import static com.stunapps.fearlessjumper.animation.AnimationState.HURT;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationState.TERMINATED;

/**
 * Created by anand.verma on 12/01/18.
 */

public class PlayerPrefab extends Prefab
{
    public PlayerPrefab()
    {

        int x = 4 * Constants.SCREEN_WIDTH / 5;
        int y = Constants.SCREEN_HEIGHT - 150;
        transform = new Transform(new Position(x,
                y), new Transform.Rotation(), new Transform.Scale());
        Bitmap alien = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue);
        Bitmap alienHurt = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue_hurt);

        Animation alienAnim = new Animation(new Bitmap[]{alien}, 0.5f);
        Animation alienHurtAnim = new Animation(new Bitmap[]{alienHurt}, 0.5f);

        Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
        stateAnimationMap.put(IDLE, alienAnim);
        stateAnimationMap.put(HURT, alienHurtAnim);
        stateAnimationMap.put(TERMINATED, alienHurtAnim);

        StateMachine animationStateMachine = StateMachine.builder().startState(IDLE)
                .from(IDLE).onEvent(AnimationEvent.HURT).toState(HURT)
                .from(HURT).onEvent(TERMINATE).toState(TERMINATED)
                .from(IDLE).onEvent(TERMINATE).toState(TERMINATED)
                .from(HURT).onEvent(AnimationEvent.HURT).toState(HURT)
                .from(HURT).onCountDown(2).toState(IDLE).build();

        components.add(new AnimatorComponent(stateAnimationMap, new Delta(0, 0), alien.getWidth(),
                alien.getHeight(), animationStateMachine));

        components.add(new RectCollider(new Delta(0, 0), alien.getWidth(), alien.getHeight()));
        components.add(new Health(100));
        components.add(new PhysicsComponent(50, new PhysicsComponent.Velocity()));
        OrientationInput orientationInput = DI.di().getInstance(OrientationInput.class);
        components.add(orientationInput);
        components.add(new PlayerComponent());

    }
}
