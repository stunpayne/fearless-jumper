package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

public abstract class Prefab
{
    public List<Component> components;

    public Prefab()
    {
        this.components = new ArrayList<>();;
    }
}
