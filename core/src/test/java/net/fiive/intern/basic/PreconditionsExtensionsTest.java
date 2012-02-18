package net.fiive.intern.basic;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PreconditionsExtensionsTest {

	@Test
	public void shouldRunFineWithCollectionThatDoesNotContainNull() {
		PreconditionsExtensions.checkCollectionDoesNotContainNull(Arrays.asList(1,2,3));
		PreconditionsExtensions.checkCollectionDoesNotContainNull(Arrays.asList(1));
		PreconditionsExtensions.checkCollectionDoesNotContainNull(new HashSet<Object>());
	}

	@Test(dataProvider = "collectionsWithNull")
	public void shouldThrowExceptionWhenCollectionContainsNull(Integer...collectionElements) {
		List<Integer> collection = Arrays.asList(collectionElements);
		try {
			PreconditionsExtensions.checkCollectionDoesNotContainNull(collection);
			Assert.fail();
		} catch( NullPointerException exception) {
			Assert.assertEquals(exception.getMessage(), "Argument cannot contain null");
		}

		try {
			PreconditionsExtensions.checkCollectionDoesNotContainNull(collection, "message");
			Assert.fail();
		} catch( NullPointerException exception) {
			Assert.assertEquals(exception.getMessage(), "message");
		}
	}

	@DataProvider
	public Object[][] collectionsWithNull() {
		Integer[][] testData = new Integer[4][];
		testData[0] = new Integer[] {1, null, 3};
		testData[1] = new Integer[] {null};
		testData[2] = new Integer[] {null, 1};
		testData[3] = new Integer[] {1, null};
		return testData;
	}
}
