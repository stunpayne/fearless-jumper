package com.stunapps.fearlessjumper.core;

import android.util.Log;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * @author sunny.s
 * @since 10/02/18
 */

/**
 * The WeightedShuffler class takes in as input items and weights assigned to each.
 * While shuffling, it generates a random number and checks every item's weight against that
 * number. It returns the first element (in sorted order) whose weight is greater than the
 * random number generated.
 *
 * @param <Item>
 */
public class WeightedShuffler<Item> implements Shuffler<Item>
{
	/**
	 * Map of weights and items
	 */
	private Map<Item, Float> items;

	/**
	 * Total weight of all items calculated from the map
	 */
	private Float totalWeight;

	private final Random random;
	private int totalGenerated = 0;

	public WeightedShuffler(Map<Item, Float> items)
	{
		this.items = Maps.newLinkedHashMap(items);

		Float totalWeight = 0f;
		for (Float weight : items.values())
		{
			totalWeight += weight;
		}
		this.totalWeight = totalWeight;
		random = new Random();
	}

	public Item shuffle()
	{
		//        double random = Math.random();
		//        double randomWeight = random * totalWeight;
		double randomWeight = random.nextDouble() * totalWeight;
		if (randomWeight > 5f) totalGenerated++;
		for (Item item : items.keySet())
		{
			if (randomWeight <= items.get(item))
			{
				Log.v("SHUFFLE", "Shuffled item: " + item.getClass().getSimpleName());
				return item;
			}
			randomWeight -= items.get(item);
		}
		return items.keySet().iterator().next();
	}

	public static class Builder<T>
	{
		private Map<T, Float> items = new HashMap<>();

		private T tempItem;

		public Builder<T> returnItem(T item)
		{
			tempItem = item;
			return this;
		}

		public Builder<T> withWeight(Float weight)
		{
			if (tempItem == null) throw new IllegalArgumentException();
			items.put(tempItem, weight);
			tempItem = null;
			return this;
		}

		public WeightedShuffler<T> build()
		{
			ArrayList<Entry<T, Float>> entryList = new ArrayList<>(items.entrySet());
			Collections.sort(entryList, new Comparator<Entry<T, Float>>()
			{
				@Override
				public int compare(Entry<T, Float> o1, Entry<T, Float> o2)
				{
					return (int) (o1.getValue() - o2.getValue());
				}
			});
			System.out.println("Sorted entry list: " + entryList);
			LinkedHashMap<T, Float> orderedMap = new LinkedHashMap<>();
			for (Entry<T, Float> entry : entryList)
			{
				orderedMap.put(entry.getKey(), entry.getValue());
			}
			System.out.println("Sorted map: " + orderedMap);

			return new WeightedShuffler<T>(orderedMap);
		}
	}

	public int getTotalGenerated()
	{
		return totalGenerated;
	}
}
