package com.stunapps.fearlessjumper.prefab;

/**
 * Created by sunny.s on 12/01/18.
 * Note: This class should not be used before Bitmaps class is initialised
 * @see com.stunapps.fearlessjumper.core.Bitmaps
 */

public enum PrefabRef
{
    /**
     * Unit prefabs
     */
    PLAYER(new PlayerPrefab()),

    PLATFORM(new PlatformPrefab()),
    UNFRIENDLY_PLATFORM(new UnFriendlyPlatformPrefab()),
    LAND(new LandPrefab()),

    FLYING_DRAGON(new FlyingDragonPrefab()),
    FOLLOWING_DRAGON(new FollowingDragonPrefab()),
    ASSAULT_DRAGON(new AssaultDragonPrefab()),
    GROUNDED_DRAGON(new GroundedDragonPrefab()),
    BULLET(new BulletPrefab()),
    SHOOTER_DRAGON(new ShooterDragonPrefab()),

    CLOCK(new ClockPrefab()),
    CLOCK_PARTICLE(new ClockParticlePrefab()),

    TEST_SHAPE_PREFAB(new TestShapePrefab()),

    /**
     * Set prefabs
     */
    GROUNDED_DRAGON_SET(new GroundedDragonPrefabSet()),
    ;

    private Prefab prefab;

    PrefabRef(Prefab prefab)
    {
        this.prefab = prefab;
    }

    public Prefab get()
    {
        return prefab;
    }
}
