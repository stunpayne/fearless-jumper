package com.stunapps.fearlessjumper.component.damage;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunny.s on 03/01/18.
 * Damages the game object only when it comes in contact with the
 * container of this component
 */

public class ContactDamageComponent extends DamageComponent
{
    private List<CollisionFace> damageRespondingFaces;

    public ContactDamageComponent()
    {
        this(0, false, new LinkedList<>(
                Arrays.asList(CollisionFace.HORIZONTAL, CollisionFace.HORIZONTAL_REVERSE,
                              CollisionFace.VERTICAL, CollisionFace.VERTICAL_REVERSE)));
    }

    public ContactDamageComponent(int damage, boolean selfDestructOnContact)
    {
        this(damage, selfDestructOnContact, new LinkedList<>(
                Arrays.asList(CollisionFace.HORIZONTAL, CollisionFace.HORIZONTAL_REVERSE,
                              CollisionFace.VERTICAL, CollisionFace.VERTICAL_REVERSE)));
    }

    public ContactDamageComponent(int damage, boolean selfDestructOnContact,
            List<CollisionFace> damageRespondingFaces)
    {
        super(damage, DamageType.CONTACT, selfDestructOnContact);
        this.damageRespondingFaces = damageRespondingFaces;
    }

    public List<CollisionFace> getDamageRespondingFaces()
    {
        return damageRespondingFaces;
    }

    @Override
    public float damage()
    {
        return damage;
    }

    @Override
    public boolean canDamage(Entity entity)
    {
        return false;
    }

    @Override
    public ContactDamageComponent cloneComponent() throws CloneNotSupportedException
    {
        return new ContactDamageComponent(damage, selfDestructOnContact, damageRespondingFaces);
    }
}
