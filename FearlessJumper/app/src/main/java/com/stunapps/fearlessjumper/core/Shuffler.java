package com.stunapps.fearlessjumper.core;

import android.util.Log;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.SortedMap;

/**
 * Created by sunny.s on 10/02/18.
 */

public class Shuffler<Item>
{
    private SortedMap<Float, Item> items;

    public Shuffler(SortedMap<Float, Item> items)
    {
        this.items = Maps.newTreeMap(items);
    }

    public Item shuffle()
    {
        double random = Math.random();
        for (Float weight : items.keySet())
        {
            if (random <= weight)
            {
                Log.d("SHUFFLE", "Returning item: " + items.get(weight).getClass());
                return items.get(weight);
            }
        }
        return items.values().iterator().next();
    }

    public static class Builder<T>
    {
        private SortedMap<Float, T> items = Maps.newTreeMap();

        private T tempItem;

        public Builder<T> returnItem(T item)
        {
            tempItem = item;
            return this;
        }

        public Builder<T> atLessThan(Float weight)
        {
            if (tempItem == null) throw new IllegalArgumentException();
            items.put(weight, tempItem);
            tempItem = null;
            return this;
        }

        public Shuffler<T> build()
        {
            return new Shuffler<T>(items);
        }
    }
}
