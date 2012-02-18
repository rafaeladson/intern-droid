package net.fiive.intern.random;

public class RandomItemCursor<T> {

	private RandomIterator.Builder<T> iteratorBuilder;
	private RandomIterator<T> iterator;
	private T currentItem;

	public RandomItemCursor(RandomIterator.Builder<T> iteratorBuilder) {
		this.iteratorBuilder = iteratorBuilder;
		this.iterator = iteratorBuilder.build();
		goToNext();
	}

	public void goToNext() {
		if ( !iterator.hasNext()) {
			iterator = iteratorBuilder.build();
		}
		if ( iterator.hasNext()) {
			currentItem = iterator.next();
		} else {
			throw new IllegalStateException("Error: iteratorBuilder instantiated an iterator that has no items!");
		}
	}

	public T getCurrent() {
		if ( currentItem != null ) {
			return currentItem;
		} else {
			throw new IllegalStateException("Error: item is null. This should never happen");
		}
	}


}
