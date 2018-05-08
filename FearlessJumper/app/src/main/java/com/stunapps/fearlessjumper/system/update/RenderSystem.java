package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

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
import com.stunapps.fearlessjumper.component.visual.ArcShape;
import com.stunapps.fearlessjumper.component.visual.CircleShape;
import com.stunapps.fearlessjumper.component.visual.LineShape;
import com.stunapps.fearlessjumper.component.visual.RectShape;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.core.ParallaxBackground;
import com.stunapps.fearlessjumper.core.ParallaxBackground.ParallaxDrawableArea;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Iterator;
import java.util.LinkedList;
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
	private static final String TAG = RenderSystem.class.getSimpleName();

	private final ComponentManager componentManager;
	private final GameStatsManager gameStatsManager;
	private final SensorDataAdapter sensorDataAdapter;

	private static int frameNum = 0;

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

		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(new CircleShape(100, new Shape.PaintProperties(null, Color.BLUE, null, null),
								   new Vector2D(400, 500)));
		shapes.add(new RectShape(100, 200, new Shape.PaintProperties(null, Color.RED, null, null),
								 new Vector2D(500, 500)));
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

		canvas.drawColor(Color.YELLOW);
		//renderBackground();
		renderEntities();
		renderHUD();
		renderParticleEmission();

		//testing
		if (Settings.DEBUG_MODE)
		{
			renderGameStats();
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

	private void renderBackground()
	{
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

		Set<Entity> shapeEntities = componentManager.getEntities(ShapeRenderable.class);
		Paint centerPaint = new Paint();
		centerPaint.setColor(Color.BLACK);
		for (Entity shapeEntity : shapeEntities)
		{
			ShapeRenderable shapeRenderable = shapeEntity.getComponent(ShapeRenderable.class);
			Vector2D baseDelta = shapeRenderable.getDelta();
			List<Shape> shapes = shapeRenderable.getRenderables();
			Position camPosition = Cameras.getMainCamera().position;

			float azimuth = shapeEntity.getTransform().getRotation().azimuth;

			Float centerX = null;
			Float centerY = null;
			if (shapeRenderable.providesCenter())
			{
				centerX = shapeEntity.getTransform().getPosition().getX() +
						shapeRenderable.getAnchor().getX() - camPosition.x;
				centerY = shapeEntity.getTransform().getPosition().getY() +
						shapeRenderable.getAnchor().getY() - camPosition.y;
			}
			else
			{
				centerX =
						shapeEntity.getTransform().position.x + shapeRenderable.getDelta().getX() +
								(shapeRenderable.getWidth() / 2) - camPosition.x;
				centerY =
						shapeEntity.getTransform().position.y + shapeRenderable.getDelta().getY() +
								(shapeRenderable.getHeight() / 2) - camPosition.y;
			}

			canvas.save();
			canvas.rotate(azimuth, centerX, centerY);

			for (Shape shape : shapes)
			{
				PaintProperties paintProperties = shape.getPaintProperties();
				Paint paint = paintProperties.getPaint();

				switch (shape.shapeType())
				{
					case LINE:
						LineShape lineShape = (LineShape) shape;
						canvas.drawLine(shapeEntity.transform.position.x + baseDelta.getX() +
												lineShape.getStart().getX() - camPosition.x,
										shapeEntity.transform.position.y + baseDelta.getY() +
												lineShape.getStart().getY() - camPosition.y,
										shapeEntity.transform.position.x + baseDelta.getX() +
												lineShape.getEnd().getX() - camPosition.x,
										shapeEntity.transform.position.y + baseDelta.getY() +
												lineShape.getEnd().getY() - camPosition.y, paint);
						break;
					case RECT:
						RectShape rectShape = (RectShape) shape;
						canvas.drawRect(shapeEntity.transform.position.x + baseDelta.getX() +
												rectShape.getLeft() - camPosition.x,
										shapeEntity.transform.position.y + baseDelta.getY() +
												rectShape.getTop() - camPosition.y,
										shapeEntity.transform.position.x + baseDelta.getX() +
												rectShape.getRight() - camPosition.x,
										shapeEntity.transform.position.y + baseDelta.getY() +
												rectShape.getBottom() - camPosition.y, paint);
						break;
					case CIRCLE:
						CircleShape circleShape = (CircleShape) shape;
						canvas.drawCircle(shapeEntity.transform.position.x + baseDelta.getX() +
												  +circleShape.getLeft() + circleShape.getRadius
												  () -
												  camPosition.x,
										  shapeEntity.transform.position.y + baseDelta.getY() +
												  circleShape.getTop() + circleShape.getRadius() -
												  camPosition.y, circleShape.getRadius(), paint);
						break;
					case ARC:
						ArcShape arcShape = (ArcShape) shape;
						RectF oval = new RectF(shapeEntity.transform.position.x + baseDelta.getX
								() +
													   arcShape.getDelta().getX() - camPosition.x,
											   shapeEntity.transform.position.y + baseDelta.getY
													   () +
													   arcShape.getDelta().getY() - camPosition.y,
											   shapeEntity.transform.position.x + baseDelta.getX
													   () +
													   arcShape.getDelta().getX() - camPosition.x +
													   arcShape.getWidth(),
											   shapeEntity.transform.position.y + baseDelta.getY
													   () +
													   arcShape.getDelta().getY() - camPosition.y +
													   arcShape.getHeight()

						);
						canvas.drawArc(oval, arcShape.getStartAngle(), arcShape.getSweepAngle(),
									   arcShape.isUseCenter(), paint);
						break;
				}
			}

			canvas.restore();

			if (Settings.DRAW_COLLIDERS)
			{
				renderCollider(shapeEntity);
			}
		}
	}


	private void renderCollider(Entity entity)
	{
		if (!entity.hasComponent(Collider.class)) return;

		Position camPosition = Cameras.getMainCamera().position;
		RectCollider collider = (RectCollider) entity.getComponent(Collider.class);
		int left = (int) (entity.transform.position.x + collider.delta.getX() - camPosition.x);
		int top = (int) (entity.transform.position.y + collider.delta.getY() - camPosition.y);
		int right = (int) (entity.transform.position.x + collider.delta.getX() + collider.width -
				camPosition.x);
		int bottom = (int) (entity.transform.position.y + collider.delta.getY() + collider.height -
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

	private void renderEmitterParticles(Emitter emitter)
	{
		Set<Particle> particles = emitter.getParticles();
		RenderMode renderMode = emitter.getRenderMode();
		Bitmap texture = emitter.getTexture();
		int radius = emitter.getConfig().getSize();

		for (Particle particle : particles)
		{
			particlePaint.setXfermode(emitter.getBlendMode());
			if (particle.isActive)
			{
				particlePaint.setAlpha((int) (255 * particle.alpha));

				Position camPosition = Cameras.getMainCamera().position;

				float x = particle.position.x - camPosition.x;
				float y = particle.position.y - camPosition.y;

				switch (renderMode)
				{
					case SHAPE:
						particlePaint.setColor(particle.getColor());
						canvas.drawCircle(x, y, radius, particlePaint);
						break;
					case TEXTURE:
						canvas.drawBitmap(texture, x - texture.getWidth() / 2,
										  y - texture.getHeight() / 2, particlePaint);
						break;
				}
			}
			particlePaint.reset();
		}
	}

	private Paint initParticlePaint()
	{
		return new Paint();
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
}
