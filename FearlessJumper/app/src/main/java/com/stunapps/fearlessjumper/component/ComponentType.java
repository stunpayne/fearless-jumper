package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.damage.AreaDamageComponent;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.movement.ConsciousTranslation;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.component.spawnable.Pickup;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PeriodicGun;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.visual.BitmapAnimator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.ShapeAnimator;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public enum ComponentType
{
	//	Collider component type
	COLLIDER(RectCollider.class),

	//	Area Damage component type
	AREA_DAMAGE(AreaDamageComponent.class),

	//	Contact Damage component type
	CONTACT_DAMAGE(ContactDamageComponent.class),

	//	Damage component type
	DAMAGE(DamageComponent.class),

	//	Emitter component type
	EMITTER(Emitter.class),

	//	Health component type
	HEALTH(Health.class),

	//	Input component type
	INPUT(Input.class),

	//	Periodic Translation component type
	PERIODIC_TRANSLATION(PeriodicTranslation.class),

	//	Conscious Translation component type
	CONSCIOUS_TRANSLATION(ConsciousTranslation.class),

	//	Physics component type
	PHYSICS(PhysicsComponent.class),

	//	Enemy component type
	ENEMY(Enemy.class),

	//	Obstacle component type
	OBSTACLE(Obstacle.class),

	//	Pickup component type
	PICKUP(Pickup.class),

	//	Fuel component type
	FUEL(Fuel.class),

	//	Periodic Gun component type
	PERIODIC_GUN(PeriodicGun.class),

	//	Player component type
	PLAYER(PlayerComponent.class),

	//	Remaining time component type
	REMAINING_TIME(RemainingTime.class),

	//	Score component type
	SCORE(Score.class),

	//	Renderable component type
	RENDERABLE(Renderable.class),

	//	Shape Renderable component type
	SHAPE_RENDERABLE(ShapeRenderable.class),

	//	Bitmap Animator component type
	BITMAP_ANIMATOR(BitmapAnimator.class),

	//	Shape Animator component type
	SHAPE_ANIMATOR(ShapeAnimator.class);

	private Class<? extends Component> componentClass;

	ComponentType(Class<? extends Component> componentClass)
	{
		this.componentClass = componentClass;
	}

	public Class<? extends Component> getComponentClass()
	{
		return componentClass;
	}
}
