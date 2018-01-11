package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 03/01/18.
 * Damages the game object only when it comes in contact with the
 * container of this component
 */

public class ContactDamageComponent extends DamageComponent
{
    public ContactDamageComponent(int damage)
    {
        super(damage, DamageType.CONTACT);
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
