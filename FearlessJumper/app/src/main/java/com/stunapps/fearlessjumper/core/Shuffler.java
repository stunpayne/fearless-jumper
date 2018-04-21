package com.stunapps.fearlessjumper.core;

/**
 * @author sunny.s
 * @since 15/02/18.
 */

/**
 * Interface to shuffle and return from a collection of items
 *
 * @param <Item>
 */
public interface Shuffler<Item>
{
	Item shuffle();

	void restore(Item item);

	void ignore(Item item);

	void reset();
}
