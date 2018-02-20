package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impl.HurtEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

import java.util.Set;

/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
	private final GameComponentManager componentManager;

	private static long lastProcessTime = System.nanoTime();
	private static Canvas canvas = null;

	private Handler handler = new Handler();

	@Inject
	public RenderSystem(GameComponentManager componentManager, EventSystem eventSystem)
	{
		this.componentManager = componentManager;
		eventSystem.registerEventListener(HurtEvent.class, playerHurtListener);
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> entities = componentManager.getEntities(RenderableComponent.class);
		Entity player = componentManager.getEntity(PlayerComponent.class);
		if (canvas == null)
		{
			canvas = Environment.CANVAS;
		}

		//  Update all cameras
		Cameras.update();

		renderBackground();
		renderEntities(entities);
		renderHUD(player);
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	private BaseEventListener<HurtEvent> playerHurtListener = new BaseEventListener<HurtEvent>()
	{
		@Override
		public void handleEvent(HurtEvent event) throws EventException
		{
			//	Right now, this looks horrible. Need to implement a better hurt overlay
			Runnable r = new Runnable()
			{
				@Override
				public void run()
				{
					Log.i("HURT", "Player hurt");
					for (int i=255; i>100; i--)
						canvas.drawARGB(i, 255, 0, 0);
				}
			};

			handler.post(r);
		}
	};

	public static Rect getRenderRect(Entity entity)
	{
		Position camPosition = Cameras.getMainCamera().position;

		RenderableComponent renderable = entity.getComponent(RenderableComponent.class);
		int left = (int) ((entity.transform.position.x + renderable.delta.x) - camPosition.x);
		int top = (int) ((entity.transform.position.y + renderable.delta.y) - camPosition.y);
		int right = (int) ((entity.transform.position.x + renderable.delta.x + renderable.width) -
				camPosition.x);
		int bottom = (int) ((entity.transform.position.y + renderable.delta.y + renderable
				.height) -
				camPosition.y);

		return new Rect(left, top, right, bottom);
	}

	private void renderBackground()
	{
		canvas.drawColor(Color.BLACK);
	}

	private void renderEntities(Set<Entity> entities)
	{
		//  Render all objects at their current positions
		for (Entity entity : entities)
		{
			RenderableComponent component = entity.getComponent(RenderableComponent.class);
			switch (component.renderType)
			{
				case SPRITE:
					Bitmap bitmap = (Bitmap) component.getRenderable();
					Rect destRect = getRenderRect(entity);

					canvas.drawBitmap(bitmap, null, destRect, null);
					break;
				case ANIMATOR:
					bitmap = (Bitmap) component.getRenderable();
					destRect = getRenderRect(entity);

					canvas.drawBitmap(bitmap, null, destRect, null);
					break;
				default:
			}
		}
	}

	/**
	 * Temporary method mainly for testing pickups and score
	 */
	private void renderHUD(Entity player)
	{
		int timeRectTop = 80;
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.SANS_SERIF);
		paint.setTextSize(40);

		Paint fuelPaint = new Paint();
		fuelPaint.setColor(Color.CYAN);
		fuelPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);

		//	Time text
		Float remainingSeconds = player.getComponent(RemainingTime.class).getRemainingSeconds();
		String timeText = "Time: ".concat(String.valueOf(remainingSeconds.intValue()).concat(" "));
		canvas.drawText(timeText, Device.SCREEN_WIDTH / 4, timeRectTop, paint);

		//	Score text
		Float playerScore = player.getComponent(Score.class).getScore();
		String scoreText = "Score: ".concat(String.valueOf(playerScore.intValue()));
		canvas.drawText(scoreText, Device.SCREEN_WIDTH / 2, timeRectTop, paint);

		//	Fuel box
		Float fuel = player.getComponent(Fuel.class).getFuel();
		int left = 1 * Device.SCREEN_WIDTH / 8;
		int right = left + 80;
		int bottom = 15 * Device.SCREEN_HEIGHT / 16;
		int top = Math.min(bottom, bottom - 3 * fuel.intValue());
		Rect fuelRect = new Rect(left, top, right, bottom);
		canvas.drawRect(fuelRect, fuelPaint);

		//	Fuel text
		String fuelText = String.valueOf(fuel.intValue());
		canvas.drawText(fuelText, (left + right) / 2, (top + bottom) / 2, fuelTextPaint);
	}
}
