package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 03/01/18.
 */

public interface Opponent
{
    public float damage();
    public boolean canDamage(Entity gameObject);
}
