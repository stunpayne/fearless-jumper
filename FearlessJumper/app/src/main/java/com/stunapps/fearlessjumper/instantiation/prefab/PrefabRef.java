package com.stunapps.fearlessjumper.instantiation.prefab;

import com.stunapps.fearlessjumper.instantiation.prefab.impl.AssaultSmileyPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.BulletPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.ClockParticlePrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.ClockPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.RotatingWheelPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.FollowingDragonPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.LandPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.PlatformPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.PlayerPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.ShooterDragonPrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.TestShapePrefab;
import com.stunapps.fearlessjumper.instantiation.prefab.impl.UnfriendlyPlatformPrefab;

/**
 * Created by sunny.s on 12/01/18.
 * Note: This class should not be used before Bitmaps class is initialised
 *
 * @see com.stunapps.fearlessjumper.core.Bitmaps
 */

public enum PrefabRef
{
	/**
	 * Unit prefabs
	 */
	PLAYER(new PlayerPrefab()),

	//	Platform Prefab
	PLATFORM(new PlatformPrefab()),

	//	Unfriendly Platform Prefab
	UNFRIENDLY_PLATFORM(new UnfriendlyPlatformPrefab()),

	//	Land Prefab
	LAND(new LandPrefab()),

	//	Flying Dragon Prefab
	ROTATING_WHEEL(new RotatingWheelPrefab()),

	//	Following Dragon Prefab
	FOLLOWING_DRAGON(new FollowingDragonPrefab()),

	//	Assault Dragon Prefab
	ASSAULT_SMILEY(new AssaultSmileyPrefab()),

	//	Bullet Prefab
	BULLET(new BulletPrefab()),

	//	Shooter Dragon Prefab
	SHOOTER_DRAGON(new ShooterDragonPrefab()),

	//	Clock Prefab
	CLOCK(new ClockPrefab()),

	//	Clock Particle Prefab
	CLOCK_PARTICLE(new ClockParticlePrefab()),

	//	Test Shape Prefab
	TEST_SHAPE_PREFAB(new TestShapePrefab());

	private Prefab prefab;

	PrefabRef(Prefab prefab)
	{
		this.prefab = prefab;
	}

	public static PrefabRef from(Prefab prefab)
	{
		for (PrefabRef prefabRef : values())
		{
			if (prefabRef.prefab == prefab)
			{
				return prefabRef;
			}
		}
		return null;
	}

	public Prefab get()
	{
		return prefab;
	}
}
