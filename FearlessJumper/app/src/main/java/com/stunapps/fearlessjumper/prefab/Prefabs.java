package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;

import java.util.List;

/**
 * Created by sunny.s on 12/01/18.
 */

public enum Prefabs
{
    PLAYER(new PlayerPrefab()),
    PLATFORM(new PlatformPrefab());

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