package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;

import java.util.List;
import java.util.Set;

/**
 * Created by sunny.s on 04/01/18.
 */

public interface ComponentManager
{
    public <C extends Component> void addComponent(Entity entity, C componentType);

    public void deleteComponent(Entity entity, Class<? extends Component> componentType);

    public Component getComponent(Entity entity, Class<? extends Component> componentType);

    public boolean hasComponent(Entity entity, Class<? extends Component> componentType);

    public <C extends Component> List<C> getComponents(Entity entity);

    public void removeComponent(Entity entity, Class<? extends Component> componentType);

    public Set<Entity> getEntities(Class<? extends Component> componentType);

    public Entity getEntity(Class<? extends Component> componentType);

    public  void deleteEntity(Entity entity);
}
