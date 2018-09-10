package com.stunapps.fearlessjumper.system.update.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.emitter.Emitter.RenderMode;
import com.stunapps.fearlessjumper.component.health.Health;
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
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;
import java.util.Set;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * @author: sunny.s
 * @since 10/09/18.
 */

@Singleton
public class EntityRenderer
{
	private static Paint fuelPaint;
	private static Paint particlePaint;
	private static Paint colliderPaint;

	public EntityRenderer()
	{
		fuelPaint = initFuelPaint();
		particlePaint = initParticlePaint();
		colliderPaint = initColliderPaint();
	}

	public static void renderEntities(ComponentManager componentManager, Canvas canvas)
	{
		Set<Entity> entities = componentManager.getEntities(Renderable.class);
		Set<Entity> shapeEntities = componentManager.getEntities(ShapeRenderable.class);
		renderEntities(entities, shapeEntities, canvas);

		Entity player = componentManager.getEntity(PlayerComponent.class);
		renderHUD(player, canvas, fuelPaint);

		Set<Entity> emitterEntities = componentManager.getEntities(Emitter.class);
		renderParticleEmission(emitterEntities, canvas);
	}


	private static void renderEntities(Set<Entity> renderableEntities, Set<Entity> shapeEntities,
			Canvas canvas)
	{
		//  Render all objects at their current positions
		for (Entity entity : renderableEntities)
		{
			Renderable component = entity.getComponent(Renderable.class);
			Bitmap bitmap = component.getRenderable();
			Rect destRect = RenderSystem.getRenderRect(entity);

			canvas.drawBitmap(bitmap, null, destRect, null);

			if (Settings.DRAW_COLLIDERS)
			{
				renderCollider(entity, canvas);
			}
		}

		Paint centerPaint = new Paint();
		centerPaint.setColor(Color.BLACK);
		for (Entity shapeEntity : shapeEntities)
		{
			ShapeRenderable shapeRenderable = shapeEntity.getComponent(ShapeRenderable.class);
			Vector2D baseDelta = shapeRenderable.getDelta();
			List<Shape> shapes = shapeRenderable.getRenderables();
			Position camPosition = Cameras.getMainCamera().position;

			float azimuth = shapeEntity.getTransform().getRotation().azimuth;

			Float centerX;
			Float centerY;
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
				if (shapeRenderable.isShadowed())
				{
					paint.setShadowLayer(10, 2, 3, Color.rgb(0, 0, 0));
				}

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
										+circleShape.getLeft() + circleShape.getRadius() -
										camPosition.x,
								shapeEntity.transform.position.y + baseDelta.getY() +
										circleShape.getTop() + circleShape.getRadius() -
										camPosition.y, circleShape.getRadius(), paint);
						break;
					case PATH:
						Path path = new Path();

						PathShape pathShape = (PathShape) shape;

						Vector2D moveTo = pathShape.getMoveTo();
						path.moveTo(shapeEntity.transform.position.x + moveTo.getX() +
										baseDelta.getX() - camPosition.x,
								shapeEntity.transform.position.y + moveTo.getY() +
										baseDelta.getY() - camPosition.y);

						Vector2D quadTo1 = pathShape.getQuadTo1();
						Vector2D quadTo2 = pathShape.getQuadTo2();
						path.rQuadTo(quadTo1.getX() + baseDelta.getX(),
								quadTo1.getY() + baseDelta.getY(),
								quadTo2.getX() + baseDelta.getX(),
								quadTo2.getY() + baseDelta.getY());

						canvas.drawPath(path, paint);
						break;
					case ARC:
						ArcShape arcShape = (ArcShape) shape;
						RectF oval = new RectF(shapeEntity.transform.position.x + baseDelta.getX
								() +
								arcShape.getDelta().getX() - camPosition.x,
								shapeEntity.transform.position.y + baseDelta.getY() +
										arcShape.getDelta().getY() - camPosition.y,
								shapeEntity.transform.position.x + baseDelta.getX() +
										arcShape.getDelta().getX() - camPosition.x +
										arcShape.getWidth(),
								shapeEntity.transform.position.y + baseDelta.getY() +
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
				renderCollider(shapeEntity, canvas);
			}
		}
	}


	private static void renderCollider(Entity entity, Canvas canvas)
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


	private static void renderParticleEmission(Set<Entity> emitterEntities, Canvas canvas)
	{
		for (Entity emitterEntity : emitterEntities)
		{
			Emitter emitter = emitterEntity.getComponent(Emitter.class);
			if (emitter.isInitialised())
			{
				renderEmitterParticles(emitter, canvas);
			}
		}
	}

	private static void renderEmitterParticles(Emitter emitter, Canvas canvas)
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


	/**
	 * Temporary method mainly for testing pickups and score
	 */
	private static void renderHUD(Entity player, Canvas canvas, Paint fuelPaint)
	{
		if (player == null) return;

		//	Time text
		Float remainingSeconds = player.getComponent(RemainingTime.class).getRemainingSeconds();
		String timeText =
				" Time: ".concat(String.valueOf(remainingSeconds.intValue()).concat(" " + ""));
		MainActivity.getInstance().updateTime(timeText);

		//	Score text
		Float playerScore = player.getComponent(Score.class).getScore();
		String scoreText = " Score: ".concat(String.valueOf(playerScore.intValue()));
		MainActivity.getInstance().updateScore(scoreText);

		//	Fuel box
		Float fuel = player.getComponent(Fuel.class).getFuel();
		int left = Device.SCREEN_WIDTH / 8;
		int right = left + (int) (80 * scaleX());
		int bottom = 15 * Device.SCREEN_HEIGHT / 16;
		int fuelRectHeight = (int) (bottom - 3 * fuel.intValue() * scaleY());
		int top = Math.min(bottom, fuelRectHeight);
		Rect fuelRect = new Rect(left, top, right, bottom);
		canvas.drawRect(fuelRect, fuelPaint);

		//	Fuel text
		String fuelText = " Fuel: " + String.valueOf(fuel.intValue());
		MainActivity.getInstance().updateFuel(fuelText);

		Health health = player.getComponent(Health.class);
		String healthText = " Health: " + health.getHealth();
		MainActivity.getInstance().updateHealth(healthText);
	}


	private Paint initColliderPaint()
	{
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		return paint;
	}

	private Paint initFuelPaint()
	{
		Paint fuelPaint = new Paint();
		fuelPaint.setColor(Color.CYAN);
		fuelPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		return fuelPaint;
	}

	private Paint initParticlePaint()
	{
		return new Paint();
	}


}
