package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public abstract class DamageComponent extends Component implements Opponent
{
    protected int damage;
    public DamageType damageType;

    public DamageComponent(int damage, DamageType damageType)
    {
        this.damage = damage;
        this.damageType = damageType;
        this.componentType = DamageComponent.class;
    }

    public enum DamageType
    {
        CONTACT,
        VECTOR,
        AREA;
    }
}
