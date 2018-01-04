package com.stunapps.fearlessjumper.entity;

import java.util.Random;

/**
 * Created by sunny.s on 04/01/18.
 */

public class GameObjectManager implements EntityManager<GameObject>
{
    @Override
    public GameObject createEntity()
    {
        //TODO: GameObject should be maintained in some data structure.
        return new GameObject(new Random().nextInt());
    }

    @Override
    public void deleteEntity(GameObject gameObject)
    {
        int id = gameObject.getId();

    }

    @Override
    public Entity getEntity(int id)
    {
        return null;
    }
}
