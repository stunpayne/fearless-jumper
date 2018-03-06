package com.stunapps.fearlessjumper.core;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by anand.verma on 03/03/18.
 */

abstract public class ObjectPool<T>
{
	private static final String TAG = "ObjectPool";

	protected final Random random;
	private ConcurrentLinkedQueue<T> pool;
	private ScheduledExecutorService executorService;

	public ObjectPool(final int minSize, final int maxSize)
	{
		this.random = new Random();
		initialise(minSize);

		executorService = Executors.newSingleThreadScheduledExecutor();

		executorService.scheduleWithFixedDelay(new Runnable()
		{
			@Override
			public void run()
			{
				int size = pool.size();

				if (size < minSize)
				{
					Log.v(TAG, ": current pool size is smaller than minimum pool size. " +
							"Increasing pool size.");
					int numOfObjectToAdd = minSize + size;
					for (int i = 0; i < numOfObjectToAdd; i++)
					{
						pool.add(createObject());
					}
				}
				else if (size > maxSize)
				{
					Log.v(TAG, ": current pool size is larger than maximum pool size. " +
							"Decreasing pool size.");
					int numOfObjectToRemove = size - maxSize;
					for (int i = 0; i < numOfObjectToRemove; i++)
					{
						pool.poll();
					}
				}
			}
		}, 0l, 2l, TimeUnit.SECONDS);
	}

	public T getObject()
	{
		T object = pool.poll();

		if (object == null)
		{
			object = createObject();
		}

		return object;
	}

	public void returnObject(T object)
	{
		if (object == null)
		{
			return;
		}

		this.pool.offer(object);
	}

	public void shutdown()
	{
		if (executorService != null)
		{
			executorService.shutdown();
		}
	}

	abstract protected T createObject();

	private void initialise(int minSize)
	{
		pool = new ConcurrentLinkedQueue<>();
		for (int i = 0; i < minSize; i++)
		{
			pool.add(createObject());
		}
	}

	@Override
	public String toString()
	{
		return "ObjectPool = " + pool + "";
	}
}
