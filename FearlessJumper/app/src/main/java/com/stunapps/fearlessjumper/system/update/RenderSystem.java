package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.ParallaxBackground;
import com.stunapps.fearlessjumper.core.ParallaxBackground.ParallaxDrawableArea;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impl.HurtEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.helper.Environment.Device;

import java.util.List;
import java.util.Set;

import static com.stunapps.fearlessjumper.helper.Environment.scaleX;
import static com.stunapps.fearlessjumper.helper.Environment.scaleY;

/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
	private final ComponentManager componentManager;

	private static long lastProcessTime = System.nanoTime();
	private static Canvas canvas = null;

	private ParallaxBackground background;
	private Paint bgPaint = new Paint();

	@Inject
	public RenderSystem(ComponentManager componentManager, EventSystem eventSystem)
	{
		this.componentManager = componentManager;

		//	Initialise background bitmap
		Bitmap originalBg =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dotbg);
		Bitmap bgBitmap =
				Bitmap.createScaledBitmap(originalBg, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT,
						false);
		background = new ParallaxBackground(bgBitmap, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT);
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> entities = componentManager.getEntities(Renderable.class);
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

	@Override
	public void reset()
	{
		lastProcessTime = System.nanoTime();
		canvas = null;
	}

	public static Rect getRenderRect(Entity entity)
	{
		Position camPosition = Cameras.getMainCamera().position;

		Renderable renderable = entity.getComponent(Renderable.class);
		int left = (int) ((entity.transform.position.x + renderable.delta.x) - camPosition.x);
		int top = (int) ((entity.transform.position.y + renderable.delta.y) - camPosition.y);
		int right = (int) ((entity.transform.position.x + renderable.delta.x + renderable.width) -
				camPosition.x);
		int bottom = (int) ((entity.transform.position.y + renderable.delta.y + renderable
				.height) -
				camPosition.y);

		return new Rect(left, top, right, bottom);
	}

	/**
	 * Returns the render bounds of entity inclusive of its children
	 * The returned values are not to be considered as an enclosing rect
	 * @param entity the Entity to return the bounds for
	 * @return Rect containing bound values
	 */
	public static Rect getRenderBounds(Entity entity)
	{
		Rect rectContainingBounds = getRenderRect(entity);
		List<Entity> children = entity.getChildren();

		for (Entity child : children)
		{
			Rect childRenderRect = getRenderRect(child);
			rectContainingBounds.left = Math.min(rectContainingBounds.left, childRenderRect.left);
			rectContainingBounds.right =
					Math.max(rectContainingBounds.right, childRenderRect.right);
			rectContainingBounds.top = Math.min(rectContainingBounds.top, childRenderRect.top);
			rectContainingBounds.bottom =
					Math.max(rectContainingBounds.bottom, childRenderRect.bottom);
		}

		return rectContainingBounds;
	}

	private void renderBackground()
	{
		canvas.drawColor(Color.BLACK);

		List<ParallaxDrawableArea> drawableAreas =
				background.getDrawables(Cameras.getMainCamera().position.getY());
		for (int i = 0; i < drawableAreas.size(); i++)
		{
			ParallaxDrawableArea drawable = drawableAreas.get(i);
			canvas.drawBitmap(background.getBitmap(), null, drawable.getRenderRect(), bgPaint);
		}
	}

	private void renderEntities(Set<Entity> entities)
	{
		//  Render all objects at their current positions
		for (Entity entity : entities)
		{
			Renderable component = entity.getComponent(Renderable.class);
			Bitmap bitmap = (Bitmap) component.getRenderable();
			Rect destRect = getRenderRect(entity);

			canvas.drawBitmap(bitmap, null, destRect, null);
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
		String timeText =
				" Time: ".concat(String.valueOf(remainingSeconds.intValue()).concat(" " + ""));
		MainActivity.getInstance().updateTime(timeText);
		//canvas.drawText(timeText, Device.SCREEN_WIDTH / 4, timeRectTop, paint);

		//	Score text
		Float playerScore = player.getComponent(Score.class).getScore();
		String scoreText = " Score: ".concat(String.valueOf(playerScore.intValue()));
		MainActivity.getInstance().updateScore(scoreText);
		//canvas.drawText(scoreText, Device.SCREEN_WIDTH / 2, timeRectTop, paint);

		//	Fuel box
		Float fuel = player.getComponent(Fuel.class).getFuel();
		int left = 1 * Device.SCREEN_WIDTH / 8;
		int right = left + (int) (80 * scaleX());
		int bottom = 15 * Device.SCREEN_HEIGHT / 16;
		int fuelRectHeight = (int) (bottom - 3 * fuel.intValue() * scaleY());
		int top = Math.min(bottom, fuelRectHeight);
		Rect fuelRect = new Rect(left, top, right, bottom);
		canvas.drawRect(fuelRect, fuelPaint);

		//	Fuel text
		String fuelText = " Fuel: " + String.valueOf(fuel.intValue());
		MainActivity.getInstance().updateFuel(fuelText);
		//canvas.drawText(fuelText, (left + right) / 2, (top + bottom) / 2, fuelTextPaint);

		Health health = player.getComponent(Health.class);
		String healthText = " Health: " + health.getHealth();
		MainActivity.getInstance().updateHealth(healthText);
	}
}
