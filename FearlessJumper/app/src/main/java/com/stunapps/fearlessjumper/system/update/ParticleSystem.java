package com.stunapps.fearlessjumper.system.update;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.system.EmitterEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by anand.verma on 06/03/18.
 */

public class ParticleSystem implements UpdateSystem
{
	private static final String TAG = ParticleSystem.class.getSimpleName();

	private final ComponentManager componentManager;
	private final EntityManager entityManager;
	private static long lastProcessTime = System.nanoTime();
	private Set<Emitter> emitters = new HashSet<>();

	@Inject
	public ParticleSystem(EventSystem eventSystem, ComponentManager componentManager,
			EntityManager entityManager)
	{
		this.componentManager = componentManager;
		this.entityManager = entityManager;
		eventSystem.registerEventListener(EmitterEvent.class, collisionEventListener);
	}

	private BaseEventListener<EmitterEvent> collisionEventListener = new
			BaseEventListener<EmitterEvent>()
	{

		@Override
		public void handleEvent(EmitterEvent event) throws EventException
		{
			Set<Entry<Emitter, Transform>> emitterSet = event.getEmitters().entrySet();
			for (Entry<Emitter, Transform> emitterEntry : emitterSet)
			{
				Emitter emitter = emitterEntry.getKey();
				Transform transform = emitterEntry.getValue();
				Entity entity = entityManager.createEntity(transform);
				entity.addComponent(emitter);
				emitter.init();
			}
		}
	};

	@Override
	public void process(long deltaTime)
	{

		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);
		Environment.CANVAS.drawText("Chal gaya", Device.SCREEN_WIDTH/2, Device.SCREEN_HEIGHT/2, fuelTextPaint);

		Set<Entity> entities = componentManager.getEntities(Emitter.class);
		Log.d(TAG, "process: emitter: emitterEntities size = " + entities.size());
		if (entities != null)
		{
			Iterator<Entity> iterator = entities.iterator();
			while (iterator.hasNext())
			{
				Entity entity = iterator.next();
				Emitter emitter = entity.getComponent(Emitter.class);
				if (emitter.isInitialised())
				{
					emitter.update(deltaTime);
				}

				if (emitter.isExhausted())
				{
					iterator.remove();
					componentManager.deleteEntity(entity);
				}
			}
		}
	}

	private void render(Set<Particle> particles){
		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);
		Canvas canvas = Environment.CANVAS;
		renderParticles(particles, canvas, fuelTextPaint);
	}

	private void renderParticles(Set<Particle> particles, Canvas canvas, Paint fuelTextPaint){

		Log.d(TAG, "renderParticles: emitter: rending particles");
		for (Particle particle : particles)
		{
			fuelTextPaint.setAlpha((int) (255 * particle.alpha));
			Log.d(TAG, "renderParticles: emitter: particle is active? = " + particle.isActive);
			if (particle.isActive)
			{
				Position camPosition = Cameras.getMainCamera().position;
				canvas.drawCircle(particle.position.x - camPosition.x,
								  particle.position.y - camPosition.y, 5, fuelTextPaint);
				canvas.drawCircle(particle.position.x,
								  particle.position.y, 5, fuelTextPaint);
				canvas.drawText("Chal gaya", Device.SCREEN_WIDTH/2, Device.SCREEN_HEIGHT/2, fuelTextPaint);
				//canvas.drawPoint(particle.position.x, particle.position.y, fuelTextPaint);
			}
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		lastProcessTime = System.nanoTime();
	}
}
