package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;

import org.junit.BeforeClass;
import org.junit.Test;
import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.Map;

/**
 * Created by sunny.s on 11/02/18.
 */

public class ShufflerTest
{
	Shuffler<Integer> shuffler;

	@BeforeClass
	public static void mock()
	{
	}

	@Test
	public void testShufflerWeights()
	{
		final int testCount = 10000;

		int hundred = 100;
		int two_hundred = 200;
		int three_hundred = 300;
		shuffler = new WeightedShuffler.Builder<Integer>().returnItem(hundred).withWeight(1f)
				.returnItem(two_hundred).withWeight(7f).returnItem(three_hundred).withWeight(2f)
				.build();

		int hundreds = 0;
		int two_hundreds = 0;
		int three_hundreds = 0;

		for (int i = 0; i < testCount; i++)
		{
			Integer integer = shuffler.shuffle();
			if (integer == hundred) hundreds++;
			else if (integer == two_hundred) two_hundreds++;
			else if (integer == three_hundred) three_hundreds++;
		}

		System.out.println(
				"Final count : Hundreds: " + hundreds + " Two hundreds: " + two_hundreds + " " +
						"Three hundreds: " + three_hundreds);
		System.out.println("More than 0.5: " + ((WeightedShuffler) shuffler).getTotalGenerated());
	}

	@Test
	public void testIgnore()
	{
		final int testCount = 10000;

		shuffler = new WeightedShuffler.Builder<Integer>()
				.returnItem(100).withWeight(2f)
				.returnItem(200).withWeight(2f)
				.returnItem(300).withWeight(2f)
				.build();
		shuffle(testCount);

		shuffler.ignore(200);
		shuffle(testCount);

		shuffler.restore(200);
		shuffle(testCount);
	}

	private void shuffle(int testCount)
	{
		Map<Integer, Integer> shuffleOutputs = Maps.newHashMap();

		for (int i = 0; i < testCount; i++)
		{
			Integer shuffledInt = shuffler.shuffle();
			if (!shuffleOutputs.containsKey(shuffledInt))
			{
				shuffleOutputs.put(shuffledInt, 1);
			}
			shuffleOutputs.put(shuffledInt, shuffleOutputs.get(shuffledInt) + 1);
		}

		System.out.println("100s: " + shuffleOutputs.get(100));
		System.out.println("200s: " + shuffleOutputs.get(200));
		System.out.println("300s: " + shuffleOutputs.get(300));
		System.out.println();
	}
}
