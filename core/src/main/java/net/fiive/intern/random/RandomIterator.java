package net.fiive.intern.random;

import com.google.common.base.Preconditions;
import net.fiive.intern.basic.PreconditionsExtensions;

import java.util.*;

public class RandomIterator<T> implements Iterator<T> {

	private final PriorityQueue<T> itemsInRandomizedOrder;

	public RandomIterator(List<T> items) {
		Preconditions.checkNotNull(items);
		Preconditions.checkArgument(!items.isEmpty(), "Error: You cannot create a repository with zero items.");
		PreconditionsExtensions.checkCollectionDoesNotContainNull(items, "Argument cannot contain a null element");

		Comparator<T> queueComparator = new RandomComparator<T>();
		itemsInRandomizedOrder = new PriorityQueue<T>(items.size(), queueComparator);
		for ( T item : items) {
			itemsInRandomizedOrder.add(item);
		}

	}

	public boolean hasNext() {
		return !itemsInRandomizedOrder.isEmpty();
	}

	public T next() {
		if ( !itemsInRandomizedOrder.isEmpty() ) {
		return itemsInRandomizedOrder.remove();
		} else {
			throw new NoSuchElementException("Error: there are no more available items. Please make sure you call hasNext() and check its result before calling next()");
		}
	}

	public void remove() {
		throw new UnsupportedOperationException("remove() is not supported");
	}

	public static class Builder<T> {

		private final static Integer NUMBER_OF_ITEMS = 100;

		private RandomItemRepository<T> repository;

		public Builder(RandomItemRepository<T> repository) {
			this.repository = repository;
		}

		public RandomIterator<T> build() {
			return new RandomIterator<T>(repository.findUpToNItems(NUMBER_OF_ITEMS));
		}

	}

	private class RandomComparator<T> implements Comparator<T> {

		@Override
		public int compare(T item1, T item2) {
			int isGreaterSelector = ((int)(Math.random() * 2)) % 2;
			return (isGreaterSelector == 0 ) ? -1 : 1;
		}

	}
}
