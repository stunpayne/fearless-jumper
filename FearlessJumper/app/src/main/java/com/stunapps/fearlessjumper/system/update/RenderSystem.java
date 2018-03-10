package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.emitter.CircularEmitter;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.emitter.RotationalEmitter;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.core.ParallaxBackground;
import com.stunapps.fearlessjumper.core.ParallaxBackground.ParallaxDrawableArea;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;
import java.util.Set;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 10/01/18.
 */

public class RenderSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private final GameStatsManager gameStatsManager;

	private static long lastProcessTime = System.nanoTime();
	private static Canvas canvas = null;

	private ParallaxBackground background;
	private Paint bgPaint = new Paint();
	private Paint colliderPaint = new Paint();

	@Inject
	public RenderSystem(ComponentManager componentManager, EventSystem eventSystem,
			CircularEmitter circularEmitter, RotationalEmitter rotationalEmitter,
			GameStatsManager gameStatsManager)
	{
		this.componentManager = componentManager;

		//	Initialise background bitmap
		Bitmap originalBg =
				BitmapFactory.decodeResource(Environment.CONTEXT.getResources(), R.drawable.dotbg);
		Bitmap bgBitmap =
				Bitmap.createScaledBitmap(originalBg, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT,
						false);
		background = new ParallaxBackground(bgBitmap, Device.SCREEN_WIDTH, Device.SCREEN_HEIGHT);


		colliderPaint.setColor(Color.WHITE);
		colliderPaint.setStyle(Style.STROKE);

		this.gameStatsManager = gameStatsManager;
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

		renderBackground();
		renderEntities();
		renderHUD();

		renderParticleEmission();

		//testing
		if (Settings.DEBUG_MODE)
		{
			//renderGameStats();
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

		canvas.drawText(String.valueOf("current score : " + gameStatsManager.getCurrentScore()), x,
						y, paint);
		y += 50;
		canvas.drawText(
				String.valueOf("session high score : " + gameStatsManager.getSessionHighScore())
				, x,
				y, paint);
		y += 50;
		canvas.drawText(
				String.valueOf("global high score : " + gameStatsManager.getGlobalHighScore()), x,
				y, paint);

		y += 50;
		canvas.drawText(String.valueOf("average score : " + gameStatsManager.getAverageScore()), x,
						y, paint);

		if (!gameStatsManager.getDeathStat().isEmpty())
		{
			y += 50;
			canvas.drawText(String.valueOf("death by : " + gameStatsManager.getDeathStat()), x, y,
							paint);
		}

		y += 50;
		canvas.drawText(String.valueOf("game play count : " + gameStatsManager.getGamePlayCount()),
						x, y, paint);

		y += 50;
		int i = 1;
		for (Long previousScore : gameStatsManager.getScoreHistory())
		{
			canvas.drawText("previous score " + i + " : " + String.valueOf(previousScore), x, y,

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

			if (Settings.DEBUG_MODE)
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
	}


	private void renderParticleEmission()
	{
		Set<Entity> emitterEntities = componentManager.getEntities(Emitter.class);
		for (Entity emitterEntity : emitterEntities)
		{
			Emitter emitter = emitterEntity.getComponent(Emitter.class);
			if (emitter.isInitialised())
			{
				Set<Particle> particles = emitter.getParticles();
				renderParticles(particles);
			}
		}
	}

	private void renderParticles(Set<Particle> particles)
	{
		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);
		//fuelTextPaint.setAlpha(255);

		for (Particle particle : particles)
		{
			fuelTextPaint.setAlpha((int) (255 * particle.alpha));
			if (particle.isActive)
			{
				Position camPosition = Cameras.getMainCamera().position;
				canvas.drawCircle(particle.position.x - camPosition.x,
						particle.position.y - camPosition.y, 5, fuelTextPaint);
			}
		}
	}

	/**
	 * Temporary method mainly for testing pickups and score
	 */
	private void renderHUD()
	{
		Entity player = componentManager.getEntity(PlayerComponent.class);
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
