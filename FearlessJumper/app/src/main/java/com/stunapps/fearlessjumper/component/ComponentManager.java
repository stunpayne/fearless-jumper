package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.GameObject;

/**
 * Created by sunny.s on 04/01/18.
 */

public interface ComponentManager<E extends Entity>
{
    public void addComponent(E gameObject);

    public <C extends Component> void deleteComponent(Class<C> componentType, Entity
            gameObject);

    public <C extends Component> void getComponent(Class<C> componentType, Entity
            gameObject);
}
