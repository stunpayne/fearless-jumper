package com.stunapps.fearlessjumper.prefab;

/**
 * Created by sunny.s on 12/01/18.
 */

public enum Prefabs
{
    PLAYER(new PlayerPrefab()),
    PLATFORM(new PlatformPrefab()),
    BOUNDARY(new BoundaryPrefab()),
    LAND(new LandPrefab()),
    MIDSCREEN_COLLIDER(new MidScreenColliderPrefab()),
    DRAGON(new DragonPrefab());

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
