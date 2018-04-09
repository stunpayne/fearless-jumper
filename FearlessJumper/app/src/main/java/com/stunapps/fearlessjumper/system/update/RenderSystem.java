package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.graphics.drawable.ArgbEvaluator;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.R;
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
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.core.ParallaxBackground;
import com.stunapps.fearlessjumper.core.ParallaxBackground.ParallaxDrawableArea;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.particle.Particle;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import lombok.Setter;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;


/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
	private static final String TAG = RenderSystem.class.getSimpleName();

	private final ComponentManager componentManager;
	private final GameStatsManager gameStatsManager;
	private final SensorDataAdapter sensorDataAdapter;

	private static int frameNum = 0;
	private static int angle = 0;
	private static int MAX_RECTS = 1;
	private static int ANGLE_CHANGE_SPEED = 6;
	private static List<AngledRect> angledRects = Lists.newArrayList();

	private static long lastProcessTime = System.nanoTime();
	private static Canvas canvas = null;

	private ParallaxBackground background;
	private Paint bgPaint = new Paint();
	private Paint colliderPaint = new Paint();
	private Paint particlePaint;
	private Paint fuelPaint;

	@Setter
	private boolean shouldRenderBackground = true;

	@Inject
	public RenderSystem(ComponentManager componentManager, GameStatsManager gameStatsManager,
			SensorDataAdapter sensorDataAdapter)
	{
		this.componentManager = componentManager;
		this.sensorDataAdapter = sensorDataAdapter;
		this.gameStatsManager = gameStatsManager;

		//	Initialise background bitmap
		Bitmap originalBg =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dotbg);
		Bitmap bgBitmap =
				Bitmap.createScaledBitmap(originalBg, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT,
						false);
		background = new ParallaxBackground(bgBitmap, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT);

		colliderPaint.setColor(Color.WHITE);
		colliderPaint.setStyle(Style.STROKE);

		particlePaint = initParticlePaint();
		fuelPaint = initFuelPaint();
		angledRects = AngledRect.init();
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

		//		renderBackground();
		//		renderEntities();
		//		renderHUD();
		//		renderParticleEmission();
		renderShapes();

		//testing
		if (Settings.DEBUG_MODE)
		{
			//renderGameStats();
		}

		if (Settings.PRINT_SENSOR_DATA)
		{
			renderSensorData();
		}

		frameNum++;
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

		Iterator<Entry<String, Integer>> iterator =
				gameStatsManager.getHurtStats().entrySet().iterator();
		while (iterator.hasNext())
		{
			y += 50;
			Entry<String, Integer> entry = iterator.next();
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
		bgPaint = new Paint();
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

	private void renderBackground()
	{
		canvas.drawColor(Color.BLACK);

		if (!shouldRenderBackground) return;

		List<ParallaxDrawableArea> drawableAreas =
				background.getDrawables(Cameras.getMainCamera().position.getY());
		for (int i = 0; i < drawableAreas.size(); i++)
		{
			ParallaxDrawableArea drawable = drawableAreas.get(i);
			canvas.drawBitmap(background.getBitmap(), null, drawable.getRenderRect(), bgPaint);
		}
	}

	private void renderEntities()
	{
		Set<Entity> entities = componentManager.getEntities(Renderable.class);
		//  Render all objects at their current positions
		for (Entity entity : entities)
		{
			Renderable component = entity.getComponent(Renderable.class);
			Bitmap bitmap = component.getRenderable();
			Rect destRect = getRenderRect(entity);

			canvas.drawBitmap(bitmap, null, destRect, null);

			if (Settings.DRAW_COLLIDERS)
			{
				renderCollider(entity);
			}
		}
	}


	private void renderCollider(Entity entity)
	{
		if (!entity.hasComponent(Collider.class)) return;

		Position camPosition = Cameras.getMainCamera().position;
		RectCollider collider = (RectCollider) entity.getComponent(Collider.class);
		int left = (int) (entity.transform.position.x + collider.delta.x - camPosition.x);
		int top = (int) (entity.transform.position.y + collider.delta.y - camPosition.y);
		int right = (int) (entity.transform.position.x + collider.delta.x + collider.width -
				camPosition.x);
		int bottom = (int) (entity.transform.position.y + collider.delta.y + collider.height -
				camPosition.y);
		canvas.drawRect(left, top, right, bottom, colliderPaint);
		if (entity.hasComponent(Enemy.class))
		{
			Enemy enemy = entity.getComponent(Enemy.class);
			canvas.drawText(enemy.getEnemyType().name(), left, top, colliderPaint);
		}
	}


	private void renderParticleEmission()
	{
		Set<Entity> emitterEntities = componentManager.getEntities(Emitter.class);
		for (Entity emitterEntity : emitterEntities)
		{
			Emitter emitter = emitterEntity.getComponent(Emitter.class);
			if (emitter.isInitialised())
			{
				renderEmitterParticles(emitter);
			}
		}
	}

	private void renderShapes()
	{
		canvas.drawColor(Color.BLACK);
		Paint paint = AngledRect.paint();

		for (AngledRect angledRect : angledRects)
		{
			int pivotX = (angledRect.rect.left + angledRect.rect.right) / 2;
			int pivotY = (angledRect.rect.top + angledRect.rect.bottom) / 2;
			canvas.save();
			canvas.rotate(angledRect.angle + angle, pivotX, pivotY);
			paint.setColor(angledRect.updateColor());
			canvas.drawRect(angledRect.rect, paint);
			canvas.restore();
		}
		angle += ANGLE_CHANGE_SPEED;
		angle %= 360;
	}

	private void renderEmitterParticles(Emitter emitter)
	{
		//TODO: Test rendering logic. Once tested, add correct logic to render particles.
		Set<Particle> particles = emitter.getParticles();
		RenderMode renderMode = emitter.getRenderMode();
		Bitmap texture = emitter.getTexture();
		particlePaint.setXfermode(emitter.getBlendMode());
		int radius = emitter.getConfig().getSize();

		for (Particle particle : particles)
		{
			if (particle.isActive)
			{
				particlePaint.setColor(particle.getColor());
				particlePaint.setAlpha((int) (255 * particle.alpha));

				Position camPosition = Cameras.getMainCamera().position;

				float x = particle.position.x - camPosition.x;
				float y = particle.position.y - camPosition.y;

				switch (renderMode)
				{
					case SHAPE:
						canvas.drawCircle(x, y, radius, particlePaint);
						break;
					case TEXTURE:
						canvas.drawBitmap(texture, x - texture.getWidth() / 2,
								y - texture.getHeight() / 2, particlePaint);
						break;
				}
			}
		}
	}

	private Paint initParticlePaint()
	{
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setXfermode(new PorterDuffXfermode(Mode.ADD));
		return paint;
	}

	private Paint initFuelPaint()
	{
		Paint fuelPaint = new Paint();
		fuelPaint.setColor(Color.CYAN);
		fuelPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		return fuelPaint;
	}

	/**
	 * Temporary method mainly for testing pickups and score
	 */
	private void renderHUD()
	{
		Entity player = componentManager.getEntity(PlayerComponent.class);
		if (player == null) return;

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

	private static class AngledRect
	{
		public static int MAX_VARIATION = 200;

		private static Paint paint = null;

		public Rect rect;
		public int angle;
		public int color;
		public int colorStep;

		public static List<AngledRect> init()
		{
			final Random random = new Random();
			List<AngledRect> angledRectList = Lists.newArrayList();
			int colorDiff = Color.RED - Color.BLUE;

			for (int i = 0; i < MAX_RECTS; i++)
			{
				int width = random.nextInt(MAX_VARIATION);
				int height = random.nextInt(MAX_VARIATION);
				int left = random.nextInt(Device.SCREEN_WIDTH);
				int top = random.nextInt(Device.SCREEN_HEIGHT);


				AngledRect angledRect = new AngledRect();
				angledRect.angle = random.nextInt(360);
				angledRect.rect = new Rect(left, top, left + width, top + height);
				angledRect.colorStep = random.nextInt(colorDiff);
				angledRect.color = Color.BLUE;

				angledRectList.add(angledRect);
			}

			return angledRectList;
		}

		public static Paint paint()
		{
			if (paint == null)
			{
				paint = new Paint();
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.WHITE);
			}
			return paint;
		}

		public int updateColor()
		{
			color += colorStep;
			color %= Color.RED;
			return color;
		}
	}
}
