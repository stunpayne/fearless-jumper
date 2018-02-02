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
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.AnimatorComponent;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.core.Event;
import com.stunapps.fearlessjumper.core.FiniteStateMachine;
import com.stunapps.fearlessjumper.core.State;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_LEFT_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_LEFT_UP;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_RIGHT_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_RIGHT_UP;
import static com.stunapps.fearlessjumper.animation.AnimationEvent.TURN_UP;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_UP;
import static com.stunapps.fearlessjumper.animation.AnimationState.IDLE;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_LEFT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_LEFT_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_LEFT_UP;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_RIGHT;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_RIGHT_DOWN;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_RIGHT_UP;
import static com.stunapps.fearlessjumper.animation.AnimationState.FLY_UP;

/**
 * Created by sunny.s on 21/01/18.
 */

public class DragonPrefab extends Prefab
{
    public DragonPrefab()
    {
        transform = new Transform(new Position(Constants.SCREEN_WIDTH / 2, 200));

        Map<Event, State> eventStateMap = new HashMap<>();
        eventStateMap.put(TURN_UP, FLY_UP);
        eventStateMap.put(TURN_DOWN, FLY_DOWN);
        eventStateMap.put(TURN_LEFT, FLY_LEFT);
        eventStateMap.put(TURN_RIGHT, FLY_RIGHT);
        eventStateMap.put(TURN_RIGHT_UP, FLY_RIGHT_UP);
        eventStateMap.put(TURN_RIGHT_DOWN, FLY_RIGHT_DOWN);
        eventStateMap.put(TURN_LEFT_UP, FLY_LEFT_UP);
        eventStateMap.put(TURN_LEFT_DOWN, FLY_LEFT_DOWN);

        FiniteStateMachine playerAnimationStateMachine = FiniteStateMachine.builder().startState(IDLE)
                .from(IDLE).onEvents(eventStateMap)
                .from(FLY_DOWN).onEvents(eventStateMap)
                .from(FLY_UP).onEvents(eventStateMap)
                .from(FLY_LEFT).onEvents(eventStateMap)
                .from(FLY_RIGHT).onEvents(eventStateMap)
                .from(FLY_RIGHT_UP).onEvents(eventStateMap)
                .from(FLY_RIGHT_DOWN).onEvents(eventStateMap)
                .from(FLY_LEFT_UP).onEvents(eventStateMap)
                .from(FLY_LEFT_DOWN).onEvents(eventStateMap).build();

        Bitmap dragonSprite1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly1);
        Bitmap dragonSprite2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly2);
        Bitmap dragonSprite3 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly3);
        Bitmap dragonSprite4 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dragon_fly4);

        Animation flyRightAnim = new Animation(new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        dragonSprite1 = Bitmap.createBitmap(dragonSprite1, 0, 0, dragonSprite1.getWidth(), dragonSprite1.getHeight(), m, false);
        dragonSprite2 = Bitmap.createBitmap(dragonSprite2, 0, 0, dragonSprite2.getWidth(), dragonSprite2.getHeight(), m, false);
        dragonSprite3 = Bitmap.createBitmap(dragonSprite3, 0, 0, dragonSprite3.getWidth(), dragonSprite3.getHeight(), m, false);
        dragonSprite4 = Bitmap.createBitmap(dragonSprite4, 0, 0, dragonSprite4.getWidth(), dragonSprite4.getHeight(), m, false);
        Animation flyLeftAnim = new Animation(new Bitmap[]{dragonSprite1, dragonSprite2, dragonSprite3, dragonSprite4}, 0.5f);

        Map<AnimationState, Animation> stateAnimationMap = new HashMap<>();
        stateAnimationMap.put(FLY_RIGHT, flyRightAnim);
        stateAnimationMap.put(FLY_LEFT, flyLeftAnim);

        components.add(new AnimatorComponent(stateAnimationMap, new Delta(0, 0), dragonSprite1.getWidth(),
                dragonSprite1.getHeight(), playerAnimationStateMachine));

        components.add(new RectCollider(new Delta(0, 0), dragonSprite1.getWidth(),
                dragonSprite1.getHeight()));
        components.add(new PeriodicTranslation()
                .withXMovement(0, Constants.SCREEN_WIDTH - dragonSprite1.getWidth(), 5f));
        components.add(new MoveDownComponent());
    }
}