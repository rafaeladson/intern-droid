package net.fiive.intern.random;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircularItemCursorTest {

	@Test
	public void shouldBuildCursorForOneElement() {
		final List<Integer> listWithOneElement = Arrays.asList(1);
		RandomIterator.Builder<Integer> iteratorBuilderMock = buildMock(listWithOneElement);

		CircularItemCursor<Integer> cursor = new CircularItemCursor<Integer>(iteratorBuilderMock);
		Assert.assertEquals(cursor.getCurrent(), new Integer(1));
		cursor.goToNext();
		Assert.assertEquals(cursor.getCurrent(), new Integer(1));

		Mockito.verify(iteratorBuilderMock, Mockito.times(2)).build();
		Mockito.verifyNoMoreInteractions(iteratorBuilderMock);

		cursor.goToNext();
		Mockito.verify(iteratorBuilderMock, Mockito.times(3)).build();
		Mockito.verifyNoMoreInteractions(iteratorBuilderMock);
	}

	@Test
	public void shouldBuildCursorWithTwoElements() {
		final List<Integer> listWithTwoElements = Arrays.asList(1,2);
		RandomIterator.Builder<Integer> iteratorBuilderMock = buildMock(listWithTwoElements);

		CircularItemCursor<Integer> cursor = new CircularItemCursor<Integer>(iteratorBuilderMock);
		Assert.assertEquals( cursor.getCurrent(), new Integer(1));
		cursor.goToNext();
		Assert.assertEquals( cursor.getCurrent(), new Integer(2));
		cursor.goToNext();
		Assert.assertEquals(cursor.getCurrent(), new Integer(1));
		cursor.goToNext();
		Assert.assertEquals( cursor.getCurrent(), new Integer(2));

		Mockito.verify(iteratorBuilderMock, Mockito.times(2)).build();
		Mockito.verifyNoMoreInteractions(iteratorBuilderMock);
	}

	/**
	 * Esse teste injeta uma falha pra ver se ele chega no IllegalStateException
	 * (quando o iterator tiver que ser reinicializado e mesmo assim o hasNext() == false.
	 */
	@Test
	public void shouldFailForIllegalState() {
		final List<Integer> listWithZeroElements = new ArrayList<Integer>();
		RandomIterator.Builder<Integer> iteratorBuilder = buildMock(listWithZeroElements);

		try {
			CircularItemCursor<Integer> cursor = new CircularItemCursor<Integer>(iteratorBuilder);
			Assert.fail();
		} catch( IllegalStateException exception ) {
			Assert.assertEquals( exception.getMessage(), "Error: iteratorBuilder instantiated an iterator that has no items!");
		}

	}



	@SuppressWarnings("unchecked")
	private RandomIterator.Builder<Integer> buildMock(final List<Integer> listWithTwoElements) {
		RandomIterator.Builder<Integer> iteratorBuilderMock = (RandomIterator.Builder<Integer>) Mockito.mock(RandomIterator.Builder.class);
		Mockito.when(iteratorBuilderMock.build()).thenAnswer(new Answer<RandomIterator<Integer>>() {
			@Override
			public RandomIterator<Integer> answer(InvocationOnMock invocation) throws Throwable {
				return new NotSoRandomIteratorStub(listWithTwoElements);
			}
		} );
		return iteratorBuilderMock;
	}


}
