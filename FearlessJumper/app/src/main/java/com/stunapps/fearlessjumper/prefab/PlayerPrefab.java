package com.stunapps.fearlessjumper.prefab;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stunapps.fearlessjumper.R;

import static com.stunapps.fearlessjumper.animation.AnimationEvent.*;
import static com.stunapps.fearlessjumper.animation.AnimationState.*;

import com.stunapps.fearlessjumper.animation.AnimationState;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.SpriteComponent;
import com.stunapps.fearlessjumper.core.Event;
import com.stunapps.fearlessjumper.core.FiniteStateMachine;
import com.stunapps.fearlessjumper.core.State;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.helper.Constants;

import java.util.HashMap;
import java.util.Map;

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
        Bitmap sprite = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienblue);
        components.add(new SpriteComponent(sprite, new Delta(0, 0), sprite.getWidth(), sprite.getHeight()));


        Map<Event, State> eventStateMap = new HashMap<>();
        eventStateMap.put(TURN_UP, WALK_UP);
        eventStateMap.put(TURN_DOWN, WALK_DOWN);
        eventStateMap.put(TURN_LEFT, WALK_LEFT);
        eventStateMap.put(TURN_RIGHT, WALK_RIGHT);
        eventStateMap.put(TURN_RIGHT_UP, WALK_RIGHT_UP);
        eventStateMap.put(TURN_RIGHT_DOWN, WALK_RIGHT_DOWN);
        eventStateMap.put(TURN_LEFT_UP, WALK_LEFT_UP);
        eventStateMap.put(TURN_LEFT_DOWN, WALK_LEFT_DOWN);

        FiniteStateMachine playerAnimationStateMachine = FiniteStateMachine.builder().startState(IDLE)
                .from(IDLE).onEvents(eventStateMap)
                .from(WALK_DOWN).onEvents(eventStateMap)
                .from(WALK_UP).onEvents(eventStateMap)
                .from(WALK_LEFT).onEvents(eventStateMap)
                .from(WALK_RIGHT).onEvents(eventStateMap)
                .from(WALK_RIGHT_UP).onEvents(eventStateMap)
                .from(WALK_RIGHT_DOWN).onEvents(eventStateMap)
                .from(WALK_LEFT_UP).onEvents(eventStateMap)
                .from(WALK_LEFT_DOWN).onEvents(eventStateMap).build();

        components.add(new RectCollider(new Delta(0, 0), sprite.getWidth(), sprite.getHeight()));
        components.add(new Health(100));
        components.add(new PhysicsComponent(50, new PhysicsComponent.Velocity()));
        OrientationInput orientationInput = DI.di().getInstance(OrientationInput.class);
        components.add(orientationInput);
        components.add(new PlayerComponent());

    }

    public static void main(String[] args)
    {
        Map<Event, State> eventStateMap = new HashMap<>();
        eventStateMap.put(TURN_UP, WALK_UP);
        eventStateMap.put(TURN_DOWN, WALK_DOWN);
        eventStateMap.put(TURN_LEFT, WALK_LEFT);
        eventStateMap.put(TURN_RIGHT, WALK_RIGHT);
        eventStateMap.put(TURN_RIGHT_UP, WALK_RIGHT_UP);
        eventStateMap.put(TURN_RIGHT_DOWN, WALK_RIGHT_DOWN);
        eventStateMap.put(TURN_LEFT_UP, WALK_LEFT_UP);
        eventStateMap.put(TURN_LEFT_DOWN, WALK_LEFT_DOWN);

        FiniteStateMachine playerAnimationStateMachine = FiniteStateMachine.builder().startState(IDLE)
                .from(IDLE).onEvents(eventStateMap)
                .from(WALK_DOWN).onEvents(eventStateMap)
                .from(WALK_UP).onEvents(eventStateMap)
                .from(WALK_LEFT).onEvents(eventStateMap)
                .from(WALK_RIGHT).onEvents(eventStateMap)
                .from(WALK_RIGHT_UP).onEvents(eventStateMap)
                .from(WALK_RIGHT_DOWN).onEvents(eventStateMap)
                .from(WALK_LEFT_UP).onEvents(eventStateMap)
                .from(WALK_LEFT_DOWN).onEvents(eventStateMap).build();

        System.out.print(playerAnimationStateMachine.transitStateOnEvent(TURN_DOWN).getClass());
    }
}
