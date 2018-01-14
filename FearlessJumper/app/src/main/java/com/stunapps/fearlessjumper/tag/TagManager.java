package com.stunapps.fearlessjumper.tag;

import com.stunapps.fearlessjumper.entity.Entity;

import java.util.List;

/**
 * Created by sunny.s on 13/01/18.
 */

public interface TagManager
{
    void tagEntity(Entity entity, Tag tag);

    void untagEntity(Entity entity);

    Tag getTag(Entity entity);

    List<Entity> getEntitiesWithTag(Tag tag);
}
