package com.stunapps.fearlessjumper.entity;

/**
 * Created by sunny.s on 04/01/18.
 */

public interface EntityManager<E extends Entity>
{
    public E createEntity();

    public void deleteEntity(E entity);

    public Entity getEntity(int id);
}