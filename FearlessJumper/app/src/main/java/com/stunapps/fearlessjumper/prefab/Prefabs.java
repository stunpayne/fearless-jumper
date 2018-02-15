package com.stunapps.fearlessjumper.prefab;

/**
 * Created by sunny.s on 12/01/18.
 */

public enum Prefabs
{
    PLAYER(new PlayerPrefab()),
    PLATFORM(new PlatformPrefab()),
    BOUNDARY(new SideBoundaryPrefab()),
    LAND(new LandPrefab()),
    DRAGON(new DragonPrefab()),
    CLOCK(new ClockPrefab());

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
