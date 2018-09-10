package com.stunapps.fearlessjumper.system;

import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.rules.RuleEngine;
import com.stunapps.fearlessjumper.system.eventonly.DamageSystem;
import com.stunapps.fearlessjumper.system.eventonly.PickupSystem;
import com.stunapps.fearlessjumper.system.input.GameInputSystem;
import com.stunapps.fearlessjumper.system.input.InputSystem;
import com.stunapps.fearlessjumper.system.update.AnimationSystem;
import com.stunapps.fearlessjumper.system.update.ClockCountdownSystem;
import com.stunapps.fearlessjumper.system.update.CollisionSystem;
import com.stunapps.fearlessjumper.system.update.FuelSystem;
import com.stunapps.fearlessjumper.system.update.GenerationSystem;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem;
import com.stunapps.fearlessjumper.system.update.LowerBoundarySystem;
import com.stunapps.fearlessjumper.system.update.MovementUpdateSystem;
import com.stunapps.fearlessjumper.system.update.ParticleSystem;
import com.stunapps.fearlessjumper.system.update.PeriodicGunSystem;
import com.stunapps.fearlessjumper.system.update.PhysicsSystem;
import com.stunapps.fearlessjumper.system.update.render.RenderSystem;
import com.stunapps.fearlessjumper.system.update.ScoreUpdateSystem;
import com.stunapps.fearlessjumper.system.update.TransformUpdateSystem;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;
import com.stunapps.fearlessjumper.system.update.parameter.ParameterTuningSystem;

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
	private static final String TAG = Systems.class.getSimpleName();
	private static final String PROCESS_SUFFIX = ".Time";
	private static int frameNum = 0;

	private static long startTime = 0;
	private static long endTime = 0;

	private static final List<Class<? extends UpdateSystem>> systemOrder =
			Arrays.asList(InputUpdateSystem.class, PhysicsSystem.class, GenerationSystem.class,
					MovementUpdateSystem.class, CollisionSystem.class, LowerBoundarySystem.class,
					TransformUpdateSystem.class, ScoreUpdateSystem.class,
					ClockCountdownSystem.class, FuelSystem.class, PeriodicGunSystem.class,
					AnimationSystem.class, ParticleSystem.class, RenderSystem.class,
					ParameterTuningSystem.class);

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
			startTime = java.lang.System.nanoTime();
			system.process(deltaTime);
			endTime = java.lang.System.nanoTime();

			if (Settings.LOG_GRAPH)
			{
				Log.d(TAG.concat(PROCESS_SUFFIX),
						system.getClass().getSimpleName() + "," + frameNum + "," +
								((endTime - startTime) / Time.ONE_SECOND_MILLIS));
			}
		}
		frameNum++;
	}

	public static void processInput(MotionEvent motionEvent)
	{
		for (InputSystem system : inputSystemsInOrder)
		{
			system.processTouchInput(motionEvent);
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

	public static void reset()
	{
		Log.i(TAG, "Resetting update systems");
		for (UpdateSystem system : systemsInOrder)
		{
			system.reset();
		}
		RuleEngine.reset();
	}

	public static ArrayList<UpdateSystem> getSystemsInOrder()
	{
		return systemsInOrder;
	}
}
