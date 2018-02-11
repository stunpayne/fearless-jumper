package com.stunapps.fearlessjumper.prefab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.animation.Animation;
import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.MoveDownComponent;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.DragonComponent;
import com.stunapps.fearlessjumper.component.specific.ObstacleComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_RIGHT;
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
        transform = new Transform(new Position(Constants.SCREEN_WIDTH / 2, 200));

        StateMachine animationStateMachine = StateMachine.builder().startState(IDLE)
                .from(IDLE).onEvent(TURN_LEFT).toState(FLY_LEFT)
                .from(IDLE).onEvent(TURN_RIGHT).toState(FLY_RIGHT)
                .from(FLY_LEFT).onEvent(TURN_RIGHT).toState(FLY_RIGHT)
                .from(FLY_RIGHT).onEvent(TURN_LEFT).toState(FLY_LEFT)
                .build();

        Bitmap dragonSprite1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly1);
        Bitmap dragonSprite2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly2);
        Bitmap dragonSprite3 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly3);
        Bitmap dragonSprite4 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly4);

        Animation idleAnim = new Animation(new Bitmap[]{dragonSprite1}, 0.5f);

        Animation flyRightAnim = new Animation(new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        dragonSprite1 = Bitmap.createBitmap(dragonSprite1, 0, 0, dragonSprite1.getWidth(), dragonSprite1.getHeight(), m, false);
        dragonSprite2 = Bitmap.createBitmap(dragonSprite2, 0, 0, dragonSprite2.getWidth(), dragonSprite2.getHeight(), m, false);
        dragonSprite3 = Bitmap.createBitmap(dragonSprite3, 0, 0, dragonSprite3.getWidth(), dragonSprite3.getHeight(), m, false);
        dragonSprite4 = Bitmap.createBitmap(dragonSprite4, 0, 0, dragonSprite4.getWidth(), dragonSprite4.getHeight(), m, false);
        Animation flyLeftAnim = new Animation(new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

        Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
        stateAnimationMap.put(IDLE, idleAnim);
        stateAnimationMap.put(FLY_RIGHT, flyRightAnim);
        stateAnimationMap.put(FLY_LEFT, flyLeftAnim);

        Animator animator =
                new Animator(stateAnimationMap, new Delta(0, 0), dragonSprite1.getWidth(),
                        dragonSprite1.getHeight(), animationStateMachine);
//        animator.triggerEvent(TURN_RIGHT);
        components.add(animator);
        components.add(new DragonComponent());
        components.add(new ObstacleComponent());
        components.add(new RectCollider(new Delta(0, 0), dragonSprite1.getWidth(),
                dragonSprite1.getHeight()));
        components.add(new PeriodicTranslation()
                .withXMovement(0, Constants.SCREEN_WIDTH - dragonSprite1.getWidth(), 5f));
        components.add(new MoveDownComponent());
        components.add(new ContactDamageComponent(1));
        components.add(new PhysicsComponent(Float.MAX_VALUE, new PhysicsComponent.Velocity(), false));
    }
}