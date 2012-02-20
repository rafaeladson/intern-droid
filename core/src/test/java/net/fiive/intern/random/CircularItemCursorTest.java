package net.fiive.intern.random;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircularItemCursorTest {

	@Test
	public void shouldBuildCursorForOneElement() {
		final List<Integer> listWithOneElement = Arrays.asList(1);

		CircularItemCursor<Integer> cursor = new CircularItemCursor<Integer>(listWithOneElement);
		Assert.assertEquals(cursor.getCurrent(), new Integer(1));
		cursor.goToNext();
		Assert.assertEquals(cursor.getCurrent(), new Integer(1));

	}

	@Test
	public void shouldBuildCursorWithTwoElements() {
		final List<Integer> listWithTwoElements = Arrays.asList(1,2);

		CircularItemCursor<Integer> cursor = new CircularItemCursor<Integer>(listWithTwoElements);
		Map<Integer,Integer> returnedItems = new HashMap<Integer, Integer>();

		returnedItems.put(cursor.getCurrent(), 1);
		cursor.goToNext();
		returnedItems.put(cursor.getCurrent(), 1 );
		cursor.goToNext();

		Assert.assertTrue( returnedItems.containsKey(cursor.getCurrent()));
		returnedItems.put(cursor.getCurrent(), returnedItems.get(cursor.getCurrent()) + 1);
		cursor.goToNext();
		Assert.assertTrue( returnedItems.containsKey(cursor.getCurrent()));
		returnedItems.put(cursor.getCurrent(), returnedItems.get(cursor.getCurrent()) + 1);
		cursor.goToNext();

		Assert.assertEquals(returnedItems.keySet().size(), 2);
		for ( Integer value : returnedItems.values()) {
			Assert.assertEquals( value, new Integer(2));
		}
	}

}
