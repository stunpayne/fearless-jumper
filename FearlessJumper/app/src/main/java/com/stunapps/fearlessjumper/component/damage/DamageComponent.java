package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public abstract class DamageComponent extends Component implements Opponent
{
    protected int damage;
    public boolean selfDestructOnContact;
    public DamageType damageType;

    public DamageComponent(int damage, DamageType damageType, boolean selfDestructOnContact)
    {
        super(DamageComponent.class);
        this.damage = damage;
        this.damageType = damageType;
        this.selfDestructOnContact = selfDestructOnContact;
    }

    public enum DamageType
    {
        CONTACT,
        VECTOR,
        AREA;
    }
}
