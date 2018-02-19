package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.visual.RenderableComponent;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
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

	@Inject
	public RenderSystem(GameComponentManager componentManager)
	{
		this.componentManager = componentManager;
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

	public static Rect getRenderRect(Entity entity)
	{
		Position camPosition = Cameras.getMainCamera().position;

		RenderableComponent component = entity.getComponent(RenderableComponent.class);
		int left = (int) ((entity.transform.position.x + component.delta.x) - camPosition.x);
		int top = (int) ((entity.transform.position.y + component.delta.y) - camPosition.y);
		int right = (int) ((entity.transform.position.x + component.delta.x + component.width) -
				camPosition.x);
		int bottom = (int) ((entity.transform.position.y + component.delta.y + component.height) -
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
		//	Render time and score
		int timeRectTop = 80;

		float remainingSeconds = player.getComponent(RemainingTime.class).getRemainingSeconds();
		Float playerScore = player.getComponent(Score.class).getScore();
		String text = String.valueOf((int) remainingSeconds).concat(" ")
				.concat(String.valueOf(playerScore.intValue()));
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.SANS_SERIF);
		paint.setTextSize(100);

		canvas.drawText(text, Device.SCREEN_WIDTH / 2, timeRectTop, paint);
	}
}
