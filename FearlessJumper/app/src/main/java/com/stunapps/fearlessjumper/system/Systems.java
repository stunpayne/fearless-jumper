package com.stunapps.fearlessjumper.system;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.system.eventonly.DamageSystem;
import com.stunapps.fearlessjumper.system.eventonly.PickupSystem;
import com.stunapps.fearlessjumper.system.update.AnimationSystem;
import com.stunapps.fearlessjumper.system.update.ClockCountdownSystem;
import com.stunapps.fearlessjumper.system.update.FuelSystem;
import com.stunapps.fearlessjumper.system.update.LowerBoundarySystem;
import com.stunapps.fearlessjumper.system.update.MovementUpdateSystem;
import com.stunapps.fearlessjumper.system.update.GenerationSystem;
import com.stunapps.fearlessjumper.system.input.GameInputSystem;
import com.stunapps.fearlessjumper.system.input.InputSystem;
import com.stunapps.fearlessjumper.system.update.CollisionSystem;
import com.stunapps.fearlessjumper.system.update.PhysicsSystem;
import com.stunapps.fearlessjumper.system.update.RenderSystem;
import com.stunapps.fearlessjumper.system.update.ScoreUpdateSystem;
import com.stunapps.fearlessjumper.system.update.TransformUpdateSystem;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

@Singleton
public class Systems
{
	private static final List<Class<? extends UpdateSystem>> systemOrder =
			Arrays.asList(PhysicsSystem.class, GenerationSystem.class, MovementUpdateSystem.class,
					CollisionSystem.class, LowerBoundarySystem.class, TransformUpdateSystem.class,
					ScoreUpdateSystem.class, ClockCountdownSystem.class, FuelSystem.class,
					AnimationSystem.class, RenderSystem.class);

	//  Next lines is hacky. The variable should be list but we can't create a list of
	//  subclasses with only one element
	private static final Class<? extends InputSystem> inputSystem = GameInputSystem.class;
	private static final List<? extends Class<? extends System>> otherSystems =
			Arrays.asList(DamageSystem.class, PickupSystem.class);

	private static ArrayList<UpdateSystem> systemsInOrder = Lists.newArrayList();
	private static ArrayList<InputSystem> inputSystemsInOrder = Lists.newArrayList();

	private final SystemFactory systemFactory;

	@Inject
	public Systems(SystemFactory systemFactory)
	{
		this.systemFactory = systemFactory;
	}

	public static void process(long deltaTime)
	{
		for (UpdateSystem system : systemsInOrder)
		{
			system.process(deltaTime);
		}
	}

	public static void processInput(MotionEvent motionEvent)
	{
		for (InputSystem system : inputSystemsInOrder)
		{
			system.processInput(motionEvent);
		}
	}

	public void initialise()
	{
		for (Class<? extends UpdateSystem> systemType : systemOrder)
		{
			systemsInOrder.add(systemFactory.getUpdateSystem(systemType));
		}

		inputSystemsInOrder.add(systemFactory.getInputSystem(inputSystem));

		for (Class<? extends System> systemType : otherSystems)
		{
			systemFactory.getSystem(systemType);
		}
	}

	public void reset()
	{
		for (UpdateSystem system : systemsInOrder)
		{
			system.reset();
		}
	}

	public static ArrayList<UpdateSystem> getSystemsInOrder()
	{
		return systemsInOrder;
	}
}
