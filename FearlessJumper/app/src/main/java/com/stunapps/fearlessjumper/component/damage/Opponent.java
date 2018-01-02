package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.entity.GameObject;

/**
 * Created by sunny.s on 03/01/18.
 */

public interface Opponent
{
    public float damage();
    public boolean canDamage(GameObject gameObject);
}
