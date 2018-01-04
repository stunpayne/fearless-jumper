package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.GameObject;

/**
 * Created by sunny.s on 04/01/18.
 */

public class GameComponentManager implements ComponentManager<GameObject>
{

    @Override
    public void addComponent(GameObject gameObject)
    {

    }

    @Override
    public <C extends Component> void deleteComponent(Class<C> componentType, Entity gameObject)
    {

    }

    @Override
    public <C extends Component> void getComponent(Class<C> componentType, Entity gameObject)
    {

    }
}
