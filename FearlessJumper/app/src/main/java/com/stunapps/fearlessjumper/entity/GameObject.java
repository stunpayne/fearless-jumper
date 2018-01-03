package com.stunapps.fearlessjumper.entity;

import com.stunapps.fearlessjumper.component.transform.Transform;

/**
 * Created by sunny.s on 03/01/18.
 */

public class GameObject extends Entity
{
    public Transform transform;

    public GameObject(Transform transform, int id)
    {
        super(id);
        this.transform = transform;
    }

    public GameObject(int id)
    {
        super(id);
    }
}
