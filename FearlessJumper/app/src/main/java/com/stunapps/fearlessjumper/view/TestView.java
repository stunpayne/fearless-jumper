package com.stunapps.fearlessjumper.view;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.loop.MainThread;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.system.update.ParticleSystem;
import com.stunapps.fearlessjumper.system.update.RenderSystem;

/**
 * Created by sunny.s on 15/03/18.
 */

public class TestView extends BaseView implements SurfaceHolder.Callback
{
	private static final String TAG = TestView.class.getSimpleName();

	private final ParticleSystem mParticleSystem;
	private final RenderSystem mRenderSystem;
	private final EntityManager mEntityManager;

	private MainThread thread;
	private MainThread previousThread;
	private EmitterConfig emitterConfig = null;

	private boolean surfaceCreated = false;
	private boolean emitterCreated = false;

	@Inject
	public TestView(Context context, ParticleSystem mParticleSystem, RenderSystem renderSystem,
			EntityManager entityManager)
	{
		super(context);
		this.mParticleSystem = mParticleSystem;
		this.mRenderSystem = renderSystem;
		this.mEntityManager = entityManager;

		getHolder().addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.i(TAG, "Surface created");
		surfaceCreated = true;
		thread.resumeThread();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		surfaceCreated = false;
	}

	@Override
	public void update(long deltaTime)
	{
		if (surfaceCreated)
		{
			if (!emitterCreated)
			{
				Cameras.setMainCamera(Cameras.createFixedCamera(new Position(0, 0), true, true));
				createParticleEmitter(emitterConfig);
			}
			mParticleSystem.process(deltaTime);
			mRenderSystem.process(deltaTime);
			postInvalidate();
		}
	}

	@Override
	public void start()
	{
		Log.d(TAG, "Starting");
		try
		{
			if (previousThread != null)
			{
				previousThread.join();
			}

			thread = new MainThread(getHolder(), this, 60);
			thread.setRunning(true);
			thread.pauseThread();
			thread.start();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void pause()
	{
		try
		{
			thread.pauseThread();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void resume()
	{
		if (surfaceCreated)
		{
			Log.i(TAG, "Resuming thread");
			thread.resumeThread();
		}
	}

	@Override
	public void stop()
	{
		thread.stopThread();
		previousThread = thread;
	}

	@Override
	public void terminate()
	{
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void restartParticles(EmitterConfig emitterConfig)
	{
		mEntityManager.deleteEntities();
		this.emitterConfig = emitterConfig;

		emitterCreated = false;
	}

	private void createParticleEmitter(EmitterConfig emitterConfig)
	{
		Log.d(TAG, "Creating particle emitter");
		try
		{
			Entity entity = mEntityManager.createEntity(new Transform(
					new Position(Device.SCREEN_WIDTH / 2, 3 * Device.SCREEN_HEIGHT / 8)));
//			entity.addComponent(
//					EternalEmitter.builder().emitterShape(emitterConfig.getEmitterShape())
//							.maxParticles(emitterConfig.getMaxParticles())
//							.particleLife(emitterConfig.getParticleLife())
//							.emissionRate(emitterConfig.getEmissionRate())
//							.positionVar(emitterConfig.getPositionVar())
//							.maxSpeed(emitterConfig.getMaxSpeed())
//							.direction(emitterConfig.getDirection())
//							.directionVar(emitterConfig.getDirectionVar()).build());
			entity.addComponent(new EternalEmitter(emitterConfig));
			entity.getComponent(Emitter.class).init();
			entity.getComponent(Emitter.class).activate();
			emitterCreated = true;
		}
		catch (CloneNotSupportedException e)
		{
			Log.d(TAG, "Error occurred while creating particle emitter");
			e.printStackTrace();
		}
	}
}
