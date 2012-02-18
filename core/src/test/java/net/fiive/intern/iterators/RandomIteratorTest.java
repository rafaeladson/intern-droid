package net.fiive.intern.iterators;

import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomIteratorTest {

	@Test
	public void testEmptyIterator() {
		try {
			RandomIterator<Integer> iterator = new RandomIterator<Integer>(new ArrayList<Integer>());
			Assert.fail();
		} catch ( IllegalArgumentException exception ) {
			Assert.assertEquals(exception.getMessage(), "Error: You cannot create a repository with zero items.");
		}
	}

	@Test
	public void testIteratorWithOneElement() {
		RandomIterator<Integer> iterator = new RandomIterator<Integer>(Arrays.asList(1));
		Assert.assertTrue( iterator.hasNext());
		Assert.assertEquals( iterator.next(),  new Integer(1));
		Assert.assertFalse( iterator.hasNext() );
	}

	@Test
	public void shouldFailWhenCallingNextAfterIteratorIsEmpty() {
		RandomIterator<Integer> iterator = new RandomIterator<Integer>(Arrays.asList(1));
		iterator.next();
		Assert.assertFalse(iterator.hasNext());
		try {
			iterator.next();
			Assert.fail();
		} catch( NoSuchElementException exception) {
			Assert.assertEquals(exception.getMessage(), "Error: there are no more available items. Please make sure you call hasNext() and check its result before calling next()");
		}
	}

	@Test(invocationCount = 10)
	public void testIteratorWithTwoElements() {
		RandomIterator<Integer> iterator = new RandomIterator<Integer>(Arrays.asList(1, 2));
		Assert.assertTrue( iterator.hasNext() );
		Integer firstElement = iterator.next();
		Assert.assertTrue( iterator.hasNext() );
		Integer secondElement = iterator.next();

		Assert.assertTrue( !firstElement.equals(secondElement));
		Assert.assertFalse(iterator.hasNext() );
	}

	@Test
	public void shouldFailToCreateIteratorWithAListThatContainsNull() {
		try {
			new RandomIterator<Integer>(Arrays.asList(1, null, 3));
			Assert.fail();
		} catch ( NullPointerException exception ) {
			Assert.assertEquals( exception.getMessage(), "Argument cannot contain a null element");
		}
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void removeShouldBeUnsupported() {
		Iterator<Integer> iterator = new RandomIterator<Integer>(Arrays.asList(1,2,3));
		iterator.remove();
	}

}
