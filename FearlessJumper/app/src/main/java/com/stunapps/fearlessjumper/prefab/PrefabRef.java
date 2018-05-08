package com.stunapps.fearlessjumper.prefab;

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
	FLYING_DRAGON(new FlyingDragonPrefab()),

	//	Following Dragon Prefab
	FOLLOWING_DRAGON(new FollowingDragonPrefab()),

	//	Assault Dragon Prefab
	ASSAULT_DRAGON(new AssaultDragonPrefab()),

	//	Grounded Dragon Prefab
	GROUNDED_DRAGON(new GroundedDragonPrefab()),

	//	Bullet Prefab
	BULLET(new BulletPrefab()),

	//	Shooter Dragon Prefab
	SHOOTER_DRAGON(new ShooterDragonPrefab()),

	//	Clock Prefab
	CLOCK(new ClockPrefab()),

	//	Clock Particle Prefab
	CLOCK_PARTICLE(new ClockParticlePrefab()),

	//	Test Shape Prefab
	TEST_SHAPE_PREFAB(new TestShapePrefab()),

	/**
	 * Set prefabs
	 */
	GROUNDED_DRAGON_SET(new GroundedDragonPrefabSet()),;

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
