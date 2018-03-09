package com.stunapps.fearlessjumper.prefab;

/**
 * Created by sunny.s on 12/01/18.
 * Note: This class should not be used before context is initialised
 */

public enum Prefabs
{
    PLAYER(new PlayerPrefab()),

    PLATFORM(new PlatformPrefab()),
    LAND(new LandPrefab()),

    DRAGON(new DragonPrefab()),
    GROUNDED_DRAGON(new GroundedDragonPrefab()),
	BULLET(new BulletPrefab()),

    CLOCK(new ClockPrefab()),
    CLOCK_PARTICLE(new ClockParticlePrefab());

    public Prefab prefab;

    Prefabs(Prefab prefab)
    {
        this.prefab = prefab;
    }

    public Prefab get()
    {
        return prefab;
    }
}
