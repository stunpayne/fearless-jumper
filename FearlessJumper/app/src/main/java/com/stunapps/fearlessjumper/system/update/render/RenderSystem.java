package com.stunapps.fearlessjumper.system.update.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.emitter.Emitter.RenderMode;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.SensorDataAdapter;
import com.stunapps.fearlessjumper.component.input.SensorDataAdapter.SensorData;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.visual.ArcShape;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.PathShape;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.ParallaxBackground;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import lombok.Setter;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;


/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private final GameStatsManager gameStatsManager;
	private final SensorDataAdapter sensorDataAdapter;

	private static int frameNum = 0;

	private static long lastProcessTime = System.nanoTime();
	private static Canvas canvas = null;

	private Paint bgPaint = new Paint();

	private Paint checkeredPaint1 = new Paint();
	private Paint checkeredPaint2 = new Paint();

	@Setter
	private boolean shouldRenderBackground = true;


	@Inject
	public RenderSystem(ComponentManager componentManager, GameStatsManager gameStatsManager,
			SensorDataAdapter sensorDataAdapter)
	{
		this.componentManager = componentManager;
		this.sensorDataAdapter = sensorDataAdapter;
		this.gameStatsManager = gameStatsManager;

		checkeredPaint1.setColor(Color.rgb(230, 230, 230));
		checkeredPaint2.setColor(Color.rgb(230, 230, 230));
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		if (canvas == null)
		{
			canvas = Environment.CANVAS;
		}

		//  Update all cameras

		Cameras.update();

		renderRects();
		EntityRenderer.renderEntities(componentManager, canvas);


		//testing
		if (Settings.DEBUG_MODE)
		{
			renderGameStats();
		}

		if (Settings.PRINT_SENSOR_DATA)
		{
			renderSensorData();
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
		canvas = null;
	}

	public static Rect getRenderRect(Entity entity)
	{
		Position camPosition = Cameras.getMainCamera().position;
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;

		if (entity.hasComponent(Renderable.class))
		{
			Renderable renderable = entity.getComponent(Renderable.class);
			left = (int) ((entity.transform.position.x + renderable.delta.getX()) - camPosition.x);
			top = (int) ((entity.transform.position.y + renderable.delta.getY()) - camPosition.y);
			right = (int) ((entity.transform.position.x + renderable.delta.getX() +
					renderable.width) - camPosition.x);
			bottom = (int) ((entity.transform.position.y + renderable.delta.getY() +
					renderable.height) - camPosition.y);
		}
		else if (entity.hasComponent(ShapeRenderable.class))
		{
			ShapeRenderable shapeRenderable = entity.getComponent(ShapeRenderable.class);
			left = (int) ((entity.transform.position.x + shapeRenderable.getDelta().getX()) -
					camPosition.x);
			top = (int) ((entity.transform.position.y + shapeRenderable.getDelta().getY()) -
					camPosition.y);
			right = (int) ((entity.transform.position.x + shapeRenderable.getDelta().getX() +
					shapeRenderable.getWidth()) - camPosition.x);
			bottom = (int) ((entity.transform.position.y + shapeRenderable.getDelta().getY() +
					shapeRenderable.getHeight()) - camPosition.y);
		}

		return new Rect(left, top, right, bottom);
	}


	private void renderSensorData()
	{
		SensorData sensorData = sensorDataAdapter.update();
		if (canvas != null)
		{
			Paint fpsPaint = new Paint();
			fpsPaint.setColor(Color.MAGENTA);
			fpsPaint.setTextSize(40);
			canvas.drawText(String.valueOf(sensorData.getPitch()), 5 * canvas.getWidth() / 12,
							5 * canvas.getHeight() / 60, fpsPaint);
			canvas.drawText(String.valueOf(sensorData.getRoll()), 5 * canvas.getWidth() / 12,
							7 * canvas.getHeight() / 60, fpsPaint);
		}
	}


	private void renderRects()
	{
		canvas.drawRect(0, 0, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT, checkeredPaint1);
		int wFactor = 20;
		int hFactor = 35;
		int width = Device.SCREEN_WIDTH / wFactor;
		int height = Device.SCREEN_HEIGHT / hFactor;
		Log.d("UNITS", "Width: " + width);
		Log.d("UNITS", "Height: " + height);

		for (int i = 0; i < wFactor; i++)
		{
			for (int j = 0; j < hFactor; j++)
			{
				int left = (i * Device.SCREEN_WIDTH) / wFactor;
				int right = left + width;

				int top = (j * Device.SCREEN_HEIGHT) / hFactor;
				int bottom = top + height;

				Paint paint = ( (i + j) %2 == 0 ) ? checkeredPaint1 : checkeredPaint2;
//				canvas.drawRect(left, top, right, bottom, paint);
			}
		}
	}


	private void renderGameStats()
	{
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.SANS_SERIF);
		paint.setTextSize(40);

		float x = Device.SCREEN_WIDTH / 2;
		float y = Device.SCREEN_HEIGHT / 2 - 300;

		canvas.drawText(String.valueOf("Score : " + gameStatsManager.getCurrentScore()), x, y,
				paint);
		y += 50;
		canvas.drawText(String.valueOf("High Score : " + gameStatsManager.getSessionHighScore())
				, x,
				y, paint);
		y += 50;
		canvas.drawText(
				String.valueOf("All Time High Score : " + gameStatsManager.getGlobalHighScore())
				, x,
				y, paint);

		y += 50;

		canvas.drawText(String.valueOf("Avg Score : " + gameStatsManager.getAverageScore()), x, y,
				paint);

		if (!gameStatsManager.getDeathStat().isEmpty())
		{
			y += 50;
			canvas.drawText(String.valueOf("Killed By : " + gameStatsManager.getDeathStat()), x, y,
					paint);
		}

		for (Entry<String, Integer> entry : gameStatsManager.getHurtStats().entrySet())
		{
			y += 50;
			canvas.drawText(entry.getKey() + "'s Hurt Count: " + entry.getValue(), x, y, paint);
		}

		y += 50;
		canvas.drawText(String.valueOf("GamePlay Count : " + gameStatsManager.getGamePlayCount()),
				x, y, paint);

		y += 50;
		int i = 1;
		for (Long previousScore : gameStatsManager.getScoreHistory())
		{
			String qualifier = "st";
			if (i == 2)
			{
				qualifier = "nd";
			}
			else if (i == 3)
			{
				qualifier = "rd";
			}
			canvas.drawText(i + qualifier + " Last Score" + " : " + String.valueOf(previousScore),
					x, y,

					paint);
			y += 50;
			i++;
		}
	}

}
