package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

public class PlatformPrefab extends Prefab
{
    public PlatformPrefab()
    {
        List<Component> components = new ArrayList<>();
        this.create(components);
    }
}
