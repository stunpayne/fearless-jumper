package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by anand.verma on 04/01/18.
 */

public class AreaDamageComponent extends DamageComponent
{
    public AreaDamageComponent(int damage)
    {
        super(damage, DamageComponent.DamageType.AREA);
    }

    @Override
    public float damage()
    {
        return damage;
    }

    @Override
    public boolean canDamage(Entity gameObject)
    {
        return false;
    }
}